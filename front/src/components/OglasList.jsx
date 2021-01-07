import React from 'react';
import OglasCard from './OglasCard';

function OglasList(props) {
    return (
        <div>
            {
                props.oglasi.map(oglas =>
                    <OglasCard key={oglas.id}
                               oglas={oglas}/>
                )
            }
        </div>

    );

}

export default OglasList;