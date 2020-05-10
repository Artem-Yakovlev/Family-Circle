const functions = require('firebase-functions');
const admin = require('firebase-admin');

// Create and Deploy Your First Cloud Functions
// https://firebase.google.com/docs/functions/write-firebase-functions

exports.helloWorld = functions.https.onRequest((request, response) => {
 response.send("Hello from Firebase!");
});

exports.notifyClientAboutKitchenChanges = functions.firestore
.document('Fridge/{foodId}')
.onUpdate((change, context) => {
  console.log("Database changed");
});
