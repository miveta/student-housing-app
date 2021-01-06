import React, {useEffect} from "react";
import {Button, Form} from 'react-bootstrap';
import {withRouter} from 'react-router-dom';
import cookie from "react-cookies";

function TrazimSobu(props) {
    const user = cookie.load('principal');
    const [grad, setGrad] = React.useState({
        id: '',
        naziv: '',
        domovi: [{
            id: '',
            imaMenzu: '',
            naziv: '',
            paviljoni: [{
                id: '',
                naziv: '',
                kategorija: ''
            }],
            checked: false
        }]
    });
    //const [domovi, setDomovi] = React.useState({d:[{id: '', imaMenzu: '', naziv: '',paviljon: {id: '', naziv: ''}, checked: false}]}) ;
    // const [paviljoni,setPaviljoni] = React.useState([{id: '', naziv: ''}]);

    const [uvjeti] = React.useState({dom: [], paviljon: [], kat: [], tipKupaonice: [], brojKreveta: [], komentar: ''});
    const katovi = ['1', '2', '3', '4'];

    const kreveti = [
        {name: "Jednokrevetna", value: "JEDNOKREVETNA"},
        {name: "Dvokrevetna", value: "DVOKREVETNA"},
        {name: "Trokrevetna", value: "JEDNOKREVETNA"},
        {name: "Nebitno", value: "NEBITNO"}
    ]
    const kupaonice = [
        {name: "Privatna", value: "PRIVATNA"},
        {name: "Zajednicka", value: "ZAJEDNICKA"},
        {name: "Nebitno", value: "NEBITNO"}
    ]

    function onChangeDom(event) {

        const {name, value} = event.target;
        setGrad(prevState => ({
            id: prevState.id,
            naziv: prevState.naziv,
            domovi: prevState.domovi.map(dom => {

                if (dom.id === value) {
                    return {
                        ...dom,
                        checked: !dom.checked
                    };
                } else {
                    return {...dom};

                }
            })
        }))
        const ids = uvjeti[name].map(el => el.id);
        if (event.target.checked) {
            uvjeti[name].push(value);
        } else {
            const index = ids.indexOf(value.id);
            uvjeti[name].splice(index, 1);
        }


    }

    function onChange(event) {
        const {name, value} = event.target;
        const ids = uvjeti[name].map(el => el.id);
        if (event.target.checked) {
            uvjeti[name].push(value);
        } else {
            const index = ids.indexOf(value.id);
            uvjeti[name].splice(index, 1);
        }


    }

    function onSubmit(e) {
        e.preventDefault();

        let body = {
            studentUsername: user.korisnickoIme,
            domId: uvjeti["dom"],
            paviljoni: uvjeti["paviljon"],
            katovi: uvjeti["kat"],
            brojKreveta: uvjeti["brojKreveta"],
            tipKupaonice: uvjeti["tipKupaonice"]
        }

        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(body)
        };

        console.log(options)

        return fetch(`${process.env.REACT_APP_BACKEND_URL}/trazimSobu/uvjetiIveta`, options)
            .then(response => {
                if (response.status === 200) {
                    //props.history.push("/")
                } else {
                    console.log(response.status)
                }
            });

    }

    useEffect(() => {
        const options = {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/trazimSobu/grad?user=${user.korisnickoIme}`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()

                } else {
                    console.log(response.status)
                }
            }).then(json => {
            setGrad(json)
        })
    }, []);


    return (

        <div className="innerForm" hidden={!props.korisnikImaSobu}>
            <Form onSubmit={onSubmit} disabled>
                <h3>Grad: {grad.naziv}</h3>


                <label>
                    Dom:

                    <br/>
                    {grad.domovi.map(dom => (
                        <li>
                            <label>
                                <input
                                    type="checkbox"
                                    onChange={onChangeDom}
                                    value={dom.id}
                                    name="dom"
                                />{dom.naziv}
                            </label>
                            {dom.checked && (
                                <div>
                                    {/*<br/>*/}
                                    <label>
                                        Paviljon:

                                        <br/>
                                        {dom.paviljoni.map(p => (
                                            <li>
                                                <label>
                                                    <input
                                                        type="checkbox"
                                                        onChange={onChange}
                                                        value={p.id}
                                                        name="paviljon"
                                                    />{p.naziv}
                                                </label>
                                            </li>
                                        ))}
                                    </label>
                                </div>)}
                        </li>
                    ))}
                </label>


                <br/>
                <label>
                    Kat:

                    <br/>
                    {katovi.map(p => (
                        <li>
                            <label>
                                <input
                                    type="checkbox"
                                    onChange={onChange}
                                    value={p}
                                    name="kat"
                                />{p}
                            </label>
                        </li>
                    ))}
                </label>

                <br/>
                <label>
                    Broj kreveta:

                    <br/>
                    {kreveti.map(p => (
                        <li>
                            <label>
                                <input
                                    type="checkbox"
                                    onChange={onChange}
                                    value={p.value}
                                    name="brojKreveta"
                                />{p.name}
                            </label>
                        </li>
                    ))}
                </label>
                <br/>
                <label>
                    Tip kupaonice:

                    <br/>
                    {kupaonice.map(p => (
                        <li>
                            <label>
                                <input
                                    type="checkbox"
                                    onChange={onChange}
                                    value={p.value}
                                    name="tipKupaonice"
                                />{p.name}
                            </label>
                        </li>
                    ))}
                </label>
                <br/>

                <br/>


                <Button type="submit" variant="dark" size="sm" block> Spremi uvjete </Button>
            </Form>
        </div>
    )
}

export default withRouter(TrazimSobu);
