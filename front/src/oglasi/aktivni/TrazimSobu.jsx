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

    const [change, setChange] = React.useState(false);

    function onChangeDom(event) {
        console.log(uvjeti);
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
        setChange(true)

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
            domId: uvjeti["domovi"],
            paviljoni: uvjeti["paviljoni"],
            katovi: uvjeti["katovi"],
            brojKreveta: uvjeti["brojKreveta"],
            tipKupaonice: uvjeti["tipKupaonice"],
        }
        props.submitUvjeti(body);
        setChange(false)
    }


    console.log(grad)
    return (

        <Form onSubmit={onSubmit} disabled className={"innerForm"}>
            <h3>Tra??im sobu</h3>
            <Form.Group>
                <Form.Label>Kat</Form.Label>
                <Row key={1}>
                    {kat.map((p, index) => (
                        <Col xs={1} key={index}>
                            <Form.Check
                                defaultChecked={uvjeti.katovi && uvjeti.katovi.includes(p)}
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
                    {props.brojKreveta.map((p, index) => (
                        <Col key={index}>
                            <Form.Check
                                defaultChecked={uvjeti.brojKreveta && uvjeti.brojKreveta.includes(p.value)}
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
                    {props.tipKupaonice.map((p, index) => (
                        <Col xs={3} key={index}>
                            <Form.Check
                                defaultChecked={uvjeti.tipKupaonice && uvjeti.tipKupaonice.includes(p.value)}
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
                    <Row key={dom.id}>
                        <Col>
                            <Form.Label>Dom</Form.Label>
                            <Form.Check
                                defaultChecked={uvjeti.domovi && uvjeti.domovi.includes(dom.id)}
                                onChange={onChangeDom}
                                key={dom.id}
                                type="checkbox"
                                id={dom.id}
                                name="domovi"
                                value={dom.id}
                                label={dom.naziv}
                            />
                            {console.log(uvjeti.domovi.includes(dom.id))}
                        </Col>
                        {
                            ((uvjeti.domovi && uvjeti.domovi.includes(dom.id)) || dom.checked) &&
                            <Col key={dom.naziv}>
                                <Form.Group>
                                    <Form.Label>Paviljoni u domu {dom.naziv}</Form.Label>
                                    {dom.paviljoni.map(p => (
                                        <Form.Check
                                            defaultChecked={uvjeti.paviljoni && uvjeti.paviljoni.includes(p.id)}
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
            <Button type="submit" variant="dark" size="lg" block disabled={!change}> Spremi promjene </Button>
        </Form>

    )


}

export default withRouter(TrazimSobu);
