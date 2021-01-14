import React from 'react';
import OglasCard from './OglasCard';

function OglasList(props) {

    let filtered = props.oglasi
    if (props.isLoggedIn) {
        if (props.oglasi.length === 0)
        //for more info, call: https://forum.freecodecamp.org/t/react-props-cant-access-object/253172/2

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
