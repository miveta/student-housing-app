import React, {useState} from "react";
import {Link} from "react-router-dom";
import {ButtonGroup, ToggleButton} from "react-bootstrap";

function OglasCard(props) {
    const oglas = props.oglas;

    const isLoggedIn = props.isLoggedIn;

    const [likeValue, setLikeValue] = useState('');

    const ocjena = [
        { name: 'Sviđa mi se', value: '1' },
        { name: 'Jako mi se sviđa', value: '2' },
        { name: 'To je to', value: '3' },
        { name: 'Nemoj više prikazivati', value: '4' },
    ];

    return (
        <div className={"Card"}>
            {oglas.naslov && <Link to={`/oglas/id=${oglas.id}`}>{oglas.naslov}</Link>}
            <br/>
            {oglas.opis && <p>{oglas.opis}</p>}
            <br/>
            <ButtonGroup size="sm" toggle>
                {ocjena.map((like, idx) => (
                    <ToggleButton
                        key={idx}
                        type="radio"
                        variant="outline-secondary"
                        value={like.value}
                        disabled={!isLoggedIn}
                        checked={likeValue === like.value}
                        onChange={(e) => setLikeValue(e.currentTarget.value)}
                    >
                        {like.name}
                    </ToggleButton>
                ))}
            </ButtonGroup>
        </div>
    );
}

export default OglasCard;