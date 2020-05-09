package com.tydeya.familycircle.ui.planpart.kitchenorganizer.barcodescanner

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.barcodescanner.BarcodeResource
import com.tydeya.familycircle.data.kitchenorganizer.barcodescanner.ScannedProduct
import com.tydeya.familycircle.databinding.ActivityBarcodeScannerBinding
import com.tydeya.familycircle.viewmodel.BarcodeScannerViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

const val CREATE_PRODUCT_FROM_BARCODE_DIALOG = "create_product_from_barcode_dialog"

class BarcodeScannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBarcodeScannerBinding
    private lateinit var viewFinder: PreviewView

    private lateinit var cameraExecutor: ExecutorService
    private var camera: Camera? = null

    private var imageCapture: ImageCapture? = null
    private var imageAnalyzer: ImageAnalysis? = null

    private var displayId: Int = -1
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK

    private var preview: Preview? = null

    private val displayManager by lazy {
        getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    }

    private lateinit var barcodeScannerViewModel: BarcodeScannerViewModel

    private var isReadyToScan = true

    private var isReadyToCreate = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        barcodeScannerViewModel = ViewModelProvider(this).get(BarcodeScannerViewModel::class.java)
        binding = ActivityBarcodeScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewFinder = binding.viewFinder

        // Initialize our background executor
        cameraExecutor = Executors.newSingleThreadExecutor()

        // Every time the orientation of device changes, update rotation for use cases
        displayManager.registerDisplayListener(displayListener, null)

        // Wait for the views to be properly laid out
        viewFinder.post {

            // Keep track of the display in which this view is attached
            displayId = viewFinder.display.displayId

            // Bind use cases
            bindCameraUseCases()
        }


        initScannerCallbackProcessing()
        binding.endScanButton.setOnClickListener {
            finish()
        }
    }

    private fun initScannerCallbackProcessing() {
        barcodeScannerViewModel.barcodeResourse.observe(this, Observer {
            it.let {
                initTitle(it)
                when (it) {
                    is BarcodeResource.AwaitingScan -> {
                        isReadyToScan = true
                        isReadyToCreate = true
                    }
                    is BarcodeResource.Success -> {
                        if (isReadyToCreate) {
                            isReadyToCreate = false
                            val newProductDialog = CreateProductFromBarcodeDialog.newInstance(it.data)
                            newProductDialog
                                    .show(supportFragmentManager, CREATE_PRODUCT_FROM_BARCODE_DIALOG)
                        }
                    }
                    is BarcodeResource.Failure -> {
                        Toast.makeText(this, getString(R.string.something_went_wrong),
                                Toast.LENGTH_LONG).show()
                        isReadyToScan = true
                    }
                }
            }
        })
    }

    private fun initTitle(resource: BarcodeResource<ScannedProduct>) {
        if (resource is BarcodeResource.Loading) {
            binding.barcodeScannerTitle.text = getString(R.string.loading)
        } else {
            binding.barcodeScannerTitle.text = getString(R.string.barcode_scanner_activity_title)
        }
    }

    /** Declare and bind preview, capture and analysis use cases */
    private fun bindCameraUseCases() {

        // Get screen metrics used to setup camera for full screen resolution
        val metrics = DisplayMetrics().also { viewFinder.display.getRealMetrics(it) }
        Log.d(TAG, "Screen metrics: ${metrics.widthPixels} x ${metrics.heightPixels}")

        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)
        Log.d(TAG, "Preview aspect ratio: $screenAspectRatio")

        val rotation = viewFinder.display.rotation

        // Bind the CameraProvider to the LifeCycleOwner
        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {

            // CameraProvider
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            preview = Preview.Builder()
                    // We request aspect ratio but no resolution
                    .setTargetAspectRatio(screenAspectRatio)
                    // Set initial target rotation
                    .setTargetRotation(rotation)
                    .build()

            // ImageCapture
            imageCapture = ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    // We request aspect ratio but no resolution to match preview config, but letting
                    // CameraX optimize for whatever specific resolution best fits our use cases
                    .setTargetAspectRatio(screenAspectRatio)
                    // Set initial target rotation, we will have to call this again if rotation changes
                    // during the lifecycle of this use case
                    .setTargetRotation(rotation)
                    .build()

            // ImageAnalysis
            imageAnalyzer = ImageAnalysis.Builder()
                    // We request aspect ratio but no resolution
                    .setTargetAspectRatio(screenAspectRatio)
                    // Set initial target rotation, we will have to call this again if rotation changes
                    // during the lifecycle of this use case
                    .setTargetRotation(rotation)
                    .build()
                    // The analyzer can then be assigned to the instance
                    .also {
                        it.setAnalyzer(cameraExecutor, BarcodeAnalyzer())
                    }

            // Must unbind the use-cases before rebinding them
            cameraProvider.unbindAll()

            try {
                // A variable number of use-cases can be passed here -
                // camera provides access to CameraControl & CameraInfo
                camera = cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview, imageCapture, imageAnalyzer)

                // Attach the viewfinder's surface provider to preview use case
                preview?.setSurfaceProvider(viewFinder.createSurfaceProvider(camera?.cameraInfo))
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        displayManager.unregisterDisplayListener(displayListener)
    }

    /**
     * We need a display listener for orientation changes that do not trigger a configuration
     * change, for example if we choose to override config change in manifest or for 180-degree
     * orientation changes.
     */
    private val displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayAdded(displayId: Int) = Unit
        override fun onDisplayRemoved(displayId: Int) = Unit
        override fun onDisplayChanged(displayId: Int) = binding.root.let { view ->
            if (displayId == this@BarcodeScannerActivity.displayId) {
                Log.d(TAG, "Rotation changed: ${view.display.rotation}")
                imageCapture?.targetRotation = view.display.rotation
                imageAnalyzer?.targetRotation = view.display.rotation
            }
        }
    }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    private inner class BarcodeAnalyzer : ImageAnalysis.Analyzer {
        private fun degreesToFirebaseRotation(degrees: Int): Int = when (degrees) {
            0 -> FirebaseVisionImageMetadata.ROTATION_0
            90 -> FirebaseVisionImageMetadata.ROTATION_90
            180 -> FirebaseVisionImageMetadata.ROTATION_180
            270 -> FirebaseVisionImageMetadata.ROTATION_270
            else -> throw Exception("Rotation must be 0, 90, 180, or 270.")
        }

        @SuppressLint("UnsafeExperimentalUsageError")
        override fun analyze(image: ImageProxy) {
            val mediaImage = image.image
            val imageRotation = degreesToFirebaseRotation(image.imageInfo.rotationDegrees)

            if (mediaImage != null && isReadyToScan) {
                startScanner(FirebaseVisionImage.fromMediaImage(mediaImage, imageRotation))
            }
            image.close()
        }

        private fun startScanner(firebaseVisionImage: FirebaseVisionImage) {
            FirebaseVision.getInstance()
                    .visionBarcodeDetector.detectInImage(firebaseVisionImage)
                    .addOnSuccessListener { barcodes ->
                        for (barcode in barcodes) {
                            if (barcode.valueType == FirebaseVisionBarcode.TYPE_PRODUCT) {
                                barcode.displayValue?.let {
                                    if (it.length == 13) {
                                        barcodeScannerViewModel.requireProductDataByBarcode(it)
                                        isReadyToScan = false
                                    }
                                }
                            }
                        }
                    }
        }
    }

    companion object {

        private const val TAG = "CameraXBasic"
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
    }
}
