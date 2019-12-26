import React from 'react';
import logo from './logo.svg';
import './App.css';
import {Link, Router} from "@reach/router";
import ExecutePayment from "./component/ExecutePayment";

function App() {
  return (
    <div className="App">
      <Router basepath={process.env.PUBLIC_URL}>
          <ExecutePayment path="/payment/:paymentId"/>
      </Router>
    </div>
  );
}

export default App;
