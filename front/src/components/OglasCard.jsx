import React, {useState} from "react";
import {Link} from "react-router-dom";
import {ButtonGroup, ToggleButton} from "react-bootstrap";

function OglasCard(props) {
    const user = JSON.parse(localStorage.getItem("user"));
    const oglas = props.oglas;

    const isLoggedIn = props.isLoggedIn;

    const [likeValue, setLikeValue] = useState('');

    const ocjena = [
        { name: 'Sviđa mi se', value: '1' },
        { name: 'Jako mi se sviđa', value: '2' },
        { name: 'To je to', value: '3' },
        { name: 'Nemoj više prikazivati', value: '4' },
    ];

    function change(e) {
        console.log("her")
        setLikeValue(e.currentTarget.value);

        const likeId = {
            id_student: user.korisnickoIme,
            id_oglas: oglas.id,
            ocjena: likeValue
        }

        const options = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(likeId)
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/lajk/update`, options)
            .then(response => {
                if (response.status === 200) {
                    console.log("lajkano");
                } else {
                    response.text().then(body => {
                        console.log(body);
                    });
                }
            }).catch(error => console.log(error));
    }

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
                        onChange={(e) => change(e)}
                    >
                        {like.name}
                    </ToggleButton>
                ))}
            </ButtonGroup>
        </div>
    );
}

export default OglasCard;