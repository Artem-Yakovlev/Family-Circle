const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();


exports.notifyClientAboutKitchenChanges = functions.firestore
.document('Fridge/{foodId}')
.onUpdate((change, context) => {
  return admin.firestore().collection('Fridge/').get().then((products) => {

    var fridgeProducts = new Object();

    products.forEach((food) => {
           var object = new Object();
           object.title = food.get('title');
           object.timestamp = food.get('shelf_life_timestamp');
           fridgeProducts[food.id] = object;
    })
    const payload = {
      data: {
        FRIDGE: JSON.stringify(fridgeProducts)
      }
    };
    return admin.messaging().sendToTopic("Kitchen", payload);
  })
});

// exports.scheduledNotifyAboutFridgeStatus = functions.pubsub
// .schedule('every 5 minutes').onRun((context) => {
//   return admin.firestore().collection('Fridge/').get().then((products) => {
//
//     let spoiledProducts = [];
//     let notFreshProducts = [];
//     let actualTimestamp = new Date().getMilliseconds();
//     products.forEach((food) => {
//            let shelfLifeTimestamp = food.get('shelf_life_timestamp');
//
//            if (shelfLifeTimestamp < actualTimestamp) {
//              spoiledProducts.push(food.get('title'));
//            } else if (shelfLifeTimestamp - actualTimestamp < 172800000){
//              notFreshProducts.push(food.get('title'))
//            }
//     })
//
//     if (spoiledProducts.length > 0 && notFreshProducts.length > 0) {
//       const messageTitle = 'Уведомление об испорченных продуктах';
//       let messageBody = ''
//
//       if (spoiledProducts.length > 0) {
//         messageBody = 'Уже испортились: ' + spoiledProducts.join(', ') + '.\n';
//       }
//
//       if (notFreshProducts.length > 0) {
//         messageBody = messageBody + 'Испортятся в течении двух дней: '
//         + notFreshProducts.join(', ') + '. ';
//       }
//
//       const payload = {
//                 notification: {
//                     title: messageTitle,
//                     body: messageBody
//                 }
//       };
//
//         return admin.messaging().sendToTopic("Kitchen", payload);
//     } else {
//         return null;
//     }
//   })
// });
