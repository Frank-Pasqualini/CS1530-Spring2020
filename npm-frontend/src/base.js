import firebase from 'firebase/app'
import 'firebase/database';
import Rebase from 're-base'

// Initalize Firebase
const config = {
  apiKey: "AIzaSyAD23ji1QnDzQU3GP265ddNFFYr6-iL6j0",
  authDomain: "jambox-2020.firebaseapp.com",
  databaseURL: "https://jambox-2020.firebaseio.com",
  projectId: "jambox-2020",
  storageBucket: "jambox-2020.appspot.com",
  messagingSenderId: "399928185645",
  appId: "1:399928185645:web:f771214d74d0e4e3a8f9a4",
  measurementId: "G-RW1G3HQQ7L"
}

const app = firebase.initializeApp(config);
const base = Rebase.createClass(app.database())


export default base;
