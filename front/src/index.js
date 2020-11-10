import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import {BrowserRouter} from "react-router-dom";
import {sha256} from "js-sha256";

export function hashPassword(passwordToBeHashed) {
   return sha256(passwordToBeHashed);

}

ReactDOM.render(
    <BrowserRouter>
        <App/>
    </BrowserRouter>

    ,
    document.getElementById("root")
);


//serviceWorker.unregister();