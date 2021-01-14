import React from 'react';
import OglasCard from './OglasCard';

function OglasList(props) {
    let oglasi = props.oglasi
        .filter(oglas => props.jmbag === null || oglas.studentJmbag !== props.jmbag)
        .filter(oglas => props.grad === null || oglas.grad === props.grad)
    return (
        <div className={"flex-row"} style={{justifyContent: 'center'}}>
            {
                oglasi.length > 1 ?
                    oglasi.map(oglas =>
                        <OglasCard key={oglas.id}
                                   oglas={oglas}/>
                    )
                    :
                    <p>Trenutno nema takvih soba :(</p>
            }
        </div>

    );
}

export default OglasList;