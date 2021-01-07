import React from "react";
import {Button, Form} from 'react-bootstrap';
import cookie from "react-cookies";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import {withRouter} from "react-router-dom";

function TrazimSobu(props) {
    const user = cookie.load('principal');
    const [grad, setGrad] = React.useState(props.grad);

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


        return fetch(`${process.env.REACT_APP_BACKEND_URL}/trazimSobu/uvjetiIveta`, options)
            .then(response => {
                if (response.status === 200) {
                    //props.history.push("/")
                } else {
                    console.log(response.status)
                }
            });

    }


    return (
        <div className="innerForm">
            <Form onSubmit={onSubmit} disabled>
                <h3>Tražim sobu</h3>
                <Form.Group>
                    <Form.Label>Kat</Form.Label>
                    <Row>
                        {katovi.map((p, index) => (
                            <Col xs={1}>
                                <Form.Check
                                    onChange={onChange}
                                    name="kat"
                                    key={index}
                                    type="checkbox"
                                    id={index}
                                    value={p}
                                    label={p}
                                />
                            </Col>
                        ))}
                    </Row>
                </Form.Group>
                <Form.Group>
                    <Form.Label>Broj kreveta</Form.Label>
                    <Row>
                        {kreveti.map((p, index) => (
                            <Col>
                                <Form.Check
                                    onChange={onChange}
                                    name="brojKreveta"
                                    key={index}
                                    type="checkbox"
                                    id={index}
                                    value={p.value}
                                    label={p.name}
                                />
                            </Col>
                        ))}
                    </Row>
                </Form.Group>
                <Form.Group>
                    <Form.Label>Tip kupaonice</Form.Label>
                    <Row>
                        {kupaonice.map((p, index) => (
                            <Col xs={3}>
                                <Form.Check
                                    onChange={onChange}
                                    name="tipKupaonice"
                                    key={index}
                                    type="checkbox"
                                    id={index}
                                    value={p.value}
                                    label={p.name}
                                /></Col>
                        ))}
                    </Row>
                </Form.Group>

                {
                    grad.domovi.map(dom => (
                        <Row>
                            <Col>
                                <Form.Label>Dom</Form.Label>
                                <Form.Check
                                    onChange={onChangeDom}
                                    key={dom.id}
                                    type="checkbox"
                                    id={dom.id}
                                    name="dom"
                                    value={dom.id}
                                    label={dom.naziv}
                                />
                            </Col>
                            {
                                dom.checked &&
                                <Col>
                                    <Form.Group>
                                        <Form.Label>Paviljoni u domu {dom.naziv}</Form.Label>
                                        {console.log(dom.paviljoni)}
                                        {dom.paviljoni.map(p => (
                                            <Form.Check
                                                onChange={onChange}
                                                name="paviljon"
                                                key={p.id}
                                                type="checkbox"
                                                id={p.id}
                                                value={p.id}
                                                label={p.naziv}>
                                            </Form.Check>
                                        ))}
                                    </Form.Group>
                                </Col>
                            }
                        </Row>
                    ))
                }
                <Button type="submit" variant="dark" block> Spremi promjene </Button>
            </Form>
        </div>
    )


}

export default withRouter(TrazimSobu);