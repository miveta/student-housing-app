import React, {Component} from "react";
import {Button, Col, Form} from 'react-bootstrap';
import cookie from "react-cookies";

class Soba extends Component {
    user;

    constructor(props) {
        super(props);
        this.state = {
            change: false,
            gradovi: [],
            domovi: [],
            paviljoni: [],
            paviljon: undefined
        };

        this.user = cookie.load('principal');
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    onSelectGrad = (event) => {
        let imeGrada = event.target.value;

        let grad = this.state.gradovi.filter(g => g.naziv === imeGrada)[0];

        this.setSelectedGrad(grad)
    }

    setSelectedGrad(grad) {
        let domovi = grad.domovi;
        let paviljoni = domovi.length === 1 ? domovi[0].paviljoni : [];
        let paviljon = paviljoni.length === 1 ? paviljoni[0] : undefined

        this.setState({
            domovi: domovi,
            paviljoni: paviljoni,
            paviljon: paviljon
        })
    }

    onSelectDom = (event) => {
        let imeDoma = event.target.value;

        let dom = this.state.domovi.filter(d => d.naziv === imeDoma)[0];

        this.setSelectedDom(dom)
    }

    setSelectedDom(dom) {
        console.log(dom)
        let paviljoni = dom.paviljoni;
        let paviljon = paviljoni.length === 1 ? paviljoni[0] : undefined

        this.setState({
            paviljoni: paviljoni,
            paviljon: paviljon
        })
    }

    onSelectPaviljon = (event) => {
        let imePaviljona = event.target.value;

        let paviljon = this.state.paviljoni.filter(p => p.naziv === imePaviljona)[0];

        this.setSelectedPaviljon(paviljon)
    }

    setSelectedPaviljon(paviljon) {
        this.setState({
            paviljon: paviljon
        })
    }

    onChange(event) {
        this.setState({change: true});
    }

    onSubmit(e) {
        e.preventDefault();
    }

    componentDidMount() {
        let self = this;
        const options = {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/soba/gradovi`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()

                } else {
                    console.log(response.status)
                }
            }).then(json => {
            self.setState({gradovi: json})
            self.setSelectedGrad(json[0])
        })

        fetch(`${process.env.REACT_APP_BACKEND_URL}/soba/student?student_username=${this.user.korisnickoIme}`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()

                } else {
                    console.log(response.status)
                }
            }).then(json => {
            console.log(json)
        })
    }

    // todo vrati ono gdje je submit disabled dok god se ne naprave promjene
    render() {
        console.log(this.state)
        return (

            <div className="middle">
                <Form onSubmit={this.onSubmit}>
                    <h3> Moja soba </h3>
                    <Form.Group>
                        <Form.Label> Grad </Form.Label>
                        <Form.Control as="select" name="grad" defaultValue="Odaberi..." onChange={this.onSelectGrad}>
                            {this.state.gradovi.map(grad => (
                                <option id={grad.id}>{grad.naziv}</option>
                            ))}
                        </Form.Control>

                        <Form.Label> Dom </Form.Label>
                        <Form.Control as="select" name="dom" onChange={this.onSelectDom}
                                      disabled={this.state.domovi.length === 0}>
                            {
                                this.state.domovi.map(dom => (
                                    <option id={dom.id}>{dom.naziv}</option>
                                ))
                            }
                        </Form.Control>
                    </Form.Group>

                    <Form.Group>
                        <Form.Row>
                            <Col xs={9}>
                                <Form.Label> Paviljon </Form.Label>
                                <Form.Control as="select" name="paviljon" onChange={this.onSelectPaviljon}
                                              disabled={this.state.paviljoni.length === 0}>
                                    {
                                        this.state.paviljoni.map(paviljon => (

                                            <option id={paviljon.id}>{paviljon.naziv}</option>
                                        ))
                                    }
                                </Form.Control>
                            </Col>
                            <Col>
                                <Form.Group>
                                    <Form.Label> Kat </Form.Label>
                                    <Form.Control name="kat" type="number" min={0}
                                                  max={this.state.paviljon === undefined ? 0 : this.state.paviljon.brojKatova}
                                                  autoCorrect={"on"} disabled={this.state.paviljon === undefined}/>
                                </Form.Group>
                            </Col>
                        </Form.Row>
                    </Form.Group>


                    <Form.Group>
                        <Form.Row>
                            <Col>
                                <Form.Label> Broj kreveta </Form.Label>
                                <Form.Control as="select" defaultValue="Odaberi...">
                                    <option> Jednokrevetna soba</option>
                                    <option> Dvokrevetna soba</option>
                                    <option> Trokrevetna soba</option>
                                    <option> Višekrevetna soba</option>
                                </Form.Control>
                            </Col>
                            <Col>
                                <Form.Label> Tip kupaonice </Form.Label>
                                <Form.Control as="select" defaultValue="Odaberi...">
                                    <option> U sobi</option>
                                    <option> Zajednički</option>
                                </Form.Control>
                            </Col>
                        </Form.Row>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> Komentar </Form.Label>
                        <Form.Control name="komentar" as="textarea" rows="3"/>
                    </Form.Group>

                    <Button type="submit" variant="dark" size="lg" block> Spremi promjene </Button>
                </Form>
            </div>
        )
    }
}

export default Soba;