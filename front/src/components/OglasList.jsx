import React from 'react';
import Oglas from './Oglas';
import Card from "./Card";

function OglasList() {
    const [oglasi, setOglasi] = React.useState([]);

    const options = {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin': '*'
        }
    };

    fetch('http://localhost:8080/oglas/list', options)
        .then(response => {
            if (response.status === 200) {
                response.json().then(body => {
                    setOglasi(body);
                }).catch(error => console.log(error))
            }
        });

    return (

        <Card title="OGLASI">
            {
                oglasi.map(oglas =>
                    <Oglas key={oglas.id}
                           oglas={oglas}/>
               )
            }
        </Card>

    );

}

export default OglasList;
