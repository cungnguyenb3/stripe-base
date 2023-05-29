importScripts('https://www.gstatic.com/firebasejs/9.1.3/firebase-app-compat.js');
importScripts('https://www.gstatic.com/firebasejs/9.1.3/firebase-messaging-compat.js');

firebase.initializeApp({
  apiKey: 'AIzaSyAjOk1CeSYpse3GkoDIdviE2tkfK7FWA14',
  authDomain: 'web-notification-f8834.firebaseapp.com',
  projectId: 'web-notification-f8834',
  storageBucket: 'web-notification-f8834.appspot.com',
  messagingSenderId: '781032428607',
  appId: '1:781032428607:web:9f8c14024f189b16d1948a',
  vapidKey: 'BD_SCg5Kh3i0g7z9s29nd4JdyltpYRzKtQcSg_12ZbhXCYDlZWDS5Jqb_Bv64V2F48-rttnrwOPsdNWR8Pkgbyo',
});
const messaging = firebase.messaging();
