import React from "react";
import Card from "./Card";

function Oglas(props) {
    const {id, naslov, opis, godina, objavljen, status, student} = props.oglas;

    function onClick() {
        window.location = 'http://localhost:3000/oglas/id=' + id;
    }

    return (
        <div onClick={onClick}>
            <Card title={naslov}>
                <p> objavljen: {objavljen} </p>
                <p> status: {status} </p>
            </Card>
        </div>

    );
}

export default Oglas;
