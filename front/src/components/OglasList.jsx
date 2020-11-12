import React from 'react';
import Oglas from './Oglas';

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
                    console.log(body)
                }).catch(error => console.log(error))
            }
        });

    return (
        <div>
            {
                oglasi.map(oglas =>
                    <Oglas key={oglas.id}
                           oglas={oglas}/>
                )
            }
        </div>
    );
}

export default OglasList;
