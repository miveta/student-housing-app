import React from 'react';
import OglasList from "../components/OglasList";

function Homepage(props) {
    return (
        <div className="middle">
            <h2>Oglasi</h2>
            <OglasList/>
        </div>
    )
}

export default Homepage;