import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import {BrowserRouter} from "react-router-dom";
import bcrypt from "bcrypt";

const saltRounds = 10;
ReactDOM.render(
    <BrowserRouter>
        <App/>
    </BrowserRouter>

    ,
    document.getElementById("root")
);


//serviceWorker.unregister();

export async function hashingPassword(password) {
    bcrypt.genSalt(saltRounds, function (err, salt) {
        bcrypt.hash(password, salt, function (err, hash) {
            if (err) {
                console.error(err)
                return
            }

            return hash
        });
    });
}

export function comparePasswordHash(password) {
    bcrypt.compare(password, hash, (err, res) => {
        if (err) {
            console.error(err)
            return
        }
        return //true or false
    })
}
