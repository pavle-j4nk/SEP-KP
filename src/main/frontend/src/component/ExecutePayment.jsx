import { Button } from "semantic-ui-react";
import React, { useEffect, useState } from "react";
import axios from "../api/AxiosInstance";

const ExecutePayment = ({ paymentId }) => {
  const [execInfo, setExecInfo] = useState({ methods: [] });

  const bankButton = url => <a href={url}><Button href={url}>Bank</Button></a>
  const paypalButton = url => <a href={url}><Button>PayPal</Button></a>
  const bitcointButton = url => <a href={url}><Button>BitCoin</Button></a>

  const getExecutionInfo = async () => {
    const { data } = await axios.get(`api/payment/${paymentId}`);
    setExecInfo(data);
  }

  useEffect(() => {
    getExecutionInfo();
  }, []);

  return (
    <>
      <h2 style={{ marginRight: 20 + 'px' }}>Complete your payment</h2>
      <h2 style={{ marginRight: 20 + 'px' }}>Amount: {execInfo.amount}</h2>


      <h2 style={{ marginRight: 20 + 'px' }}>Choose payment method</h2>
      {
        execInfo.methods.map(method => {
          if (method.method === 'BANK') return bankButton(method.executeUrl);
          else if (method.method === 'PAYPAL') return paypalButton(method.executeUrl);
          else if (method.method === 'BITCOIN') return bitcointButton(method.executeUrl);
        })
      }
    </>
  );

};

export default ExecutePayment;
