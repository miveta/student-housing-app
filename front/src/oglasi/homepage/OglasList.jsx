import React from 'react';
import OglasCard from './OglasCard';

function OglasList(props) {

    let filtered = props.oglasi
    if (props.isLoggedIn) {
        filtered = props.oglasi
            .filter(oglas => oglas.studentJmbag !== props.user.jmbag)
            .filter(oglas => oglas.grad.id === props.user.grad.id)
    }

    return (
        <div>
            {
                filtered
                    .map(filtriranOglas =>
                    <OglasCard key={filtriranOglas.id}
                               oglas={filtriranOglas}/>
                )
            }
        </div>

    );

}

export default OglasList;