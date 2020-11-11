import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import {BrowserRouter} from "react-router-dom";
import {sha256} from "js-sha256";

const base64 = require('base-64');

export function hashPassword(passwordToBeHashed) {
    let digest = sha256(Buffer.from(passwordToBeHashed, 'utf8'));
    return digest
}

ReactDOM.render(
    <BrowserRouter>
        <App/>
    </BrowserRouter>
    ,
    document.getElementById("root")
);


//serviceWorker.unregister();