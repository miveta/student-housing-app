import React from 'react';
import OglasCard from './OglasCard';

function OglasList() {
    const [oglasi, setOglasi] = React.useState([]);

    const options = {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin': '*'
        }
    };

    fetch(`${process.env.REACT_APP_BACKEND_URL}/oglas/list`, options)
        .then(response => {
            if (response.status === 200) {
                response.json().then(body => {
                    setOglasi(body);
                }).catch(error => console.log(error))
            }
        });
    korisnik;

    return (
        <div>
            {
                oglasi.map(oglas =>
                    <OglasCard key={oglas.id}
                               oglas={oglas}/>
               )
            }
        </div>

    );

}

export default OglasList;
