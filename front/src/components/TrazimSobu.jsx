import React from "react";
import {Button, Form} from 'react-bootstrap';
import cookie from "react-cookies";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import {withRouter} from "react-router-dom";

function TrazimSobu(props) {
    const user = cookie.load('principal');
    const [grad, setGrad] = React.useState(props.grad);

    const [uvjeti] = React.useState(props.uvjeti);
    const kat = [1, 2, 3, 4];

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
        console.log(uvjeti)
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
            domId: uvjeti["domId"],
            paviljoni: uvjeti["paviljoni"],
            katovi: uvjeti["katovi"],
            brojKreveta: uvjeti["brojKreveta"],
            tipKupaonice: uvjeti["tipKupaonice"]
        }
        props.submitUvjeti(body);


    }


    return (

        <Form onSubmit={onSubmit} disabled className={"innerForm"}>
            <h3>Tražim sobu</h3>
            <Form.Group>
                <Form.Label>Kat</Form.Label>
                <Row key={1}>
                    {kat.map((p, index) => (
                        <Col xs={1} key={index}>
                            <Form.Check
                                defaultChecked={uvjeti.katovi.includes(p)}
                                onChange={onChange}
                                name="katovi"
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
                <Row key={2}>
                    {kreveti.map((p, index) => (
                        <Col key={index}>
                            <Form.Check
                                defaultChecked={uvjeti.brojKreveta.includes(p.value)}
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
                <Row key={3}>
                    {kupaonice.map((p, index) => (
                        <Col xs={3} key={index}>
                            <Form.Check
                                defaultChecked={uvjeti.tipKupaonice.includes(p.value)}
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
                    <Row key={dom.id+1}>
                        <Col key={dom.id}>
                            <Form.Label>Dom</Form.Label>
                            <Form.Check
                                defaultChecked={uvjeti.domId.includes(dom.id)}
                                onChange={onChangeDom}
                                key={dom.id}
                                type="checkbox"
                                id={dom.id}
                                name="domId"
                                value={dom.id}
                                label={dom.naziv}
                            />
                        </Col>
                        {
                            (uvjeti.domId.includes(dom.id) || dom.checked) &&
                            <Col key={dom.naziv}>
                                <Form.Group>
                                    <Form.Label>Paviljoni u domu {dom.naziv}</Form.Label>
                                    {console.log(dom.paviljoni)}
                                    {dom.paviljoni.map(p => (
                                        <Form.Check
                                            defaultChecked={uvjeti.paviljoni.includes(p.id)}
                                            onChange={onChange}
                                            name="paviljoni"
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
            <Button type="submit" variant="dark" size="lg" block> Spremi promjene </Button>
        </Form>

    )


}

export default withRouter(TrazimSobu);