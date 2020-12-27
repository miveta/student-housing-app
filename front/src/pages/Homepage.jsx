import React from 'react';
import OglasList from "../components/OglasList";

function Homepage(props) {
    const isLoggedIn = props.isLoggedIn;

    return (
        <div className="middle">
            <h2>Oglasi</h2>
            <OglasList isLoggedIn={isLoggedIn}/>
        </div>
    )
}

export default Homepage;