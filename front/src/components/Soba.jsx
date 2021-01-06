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
            grad: '',
            dom: '',
            id: '',
            brojKreveta: '',
            kat: '',
            komentar: '',
            paviljon: undefined,
            tipKupaonice: ''
        };


        this.user = cookie.load('principal');
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    capitalize = (s) => {
        s = s.toLowerCase()
        return s.charAt(0).toUpperCase() + s.slice(1)
    }

    onSelectPaviljon = (event) => {
        let self = this;

        let imePaviljona = event.target.value
        let grad = self.props.gradovi.filter(g => g.naziv === self.state.grad)[0]
        let dom = grad.domovi.filter(d => d.naziv === self.state.dom)[0]
        let paviljon = dom.paviljoni.filter(p => p.naziv === imePaviljona)[0]

        self.setState({paviljon: paviljon})
    }

    getDomovi = (imeGrada) => {
        return this.props.gradovi.filter(grad =>
            grad.naziv === imeGrada
        ).map(grad =>
            grad.domovi)[0]
    }

    getPaviljoni = (imeDoma) => {
        return this.state.domovi.filter(dom =>
            dom.naziv === imeDoma
        ).map(dom =>
            dom.paviljoni)[0]
    }

    onChange = (event) => {
        const {name, value} = event.target;

        if (name === "grad") {
            let domovi = this.getDomovi(value);
            this.setState(state => ({...state, domovi: domovi}))
        } else if (name === "dom") {
            let paviljoni = this.getPaviljoni(value)
            if (paviljoni.length === 1) {
                this.setState(state => ({...state, paviljon: paviljoni[0]}))
            }
            this.setState(state => ({...state, paviljoni: paviljoni}))
        }

        this.setState(state => ({...state, [name]: value, change: true}))
    }

    onSubmit(e) {
        e.preventDefault();
        let self = this

        let soba = {
            idPaviljon: this.state.paviljon.id,
            kat: this.state.kat,
            brojKreveta: this.state.brojKreveta.toUpperCase(),
            tipKupaonice: this.state.tipKupaonice.toUpperCase(),
            komentar: this.state.komentar
        }

        self.props.submitSoba(soba)
    }

    componentDidMount() {
        let self = this;
        const options = {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        /*        fetch(`${process.env.REACT_APP_BACKEND_URL}/soba/gradovi`, options)
                    .then(response => {
                        if (response.status === 200) {
                            return response.json()
                        } else {
                            console.log(response.status)
                        }
                    }).then(json => {
                    self.setState({gradovi: json})
                })*/

        fetch(`${process.env.REACT_APP_BACKEND_URL}/soba/student?student_username=${this.user.korisnickoIme}`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                }
            }).then(json => {
            self.setState({...json})
        }).catch(e => console.log(e))
    }

    // todo vrati ono gdje je submit disabled dok god se ne naprave promjene
    render() {
        return (
            <div className="innerForm">
                <Form onSubmit={this.onSubmit}>
                    <h3> Nudim sobu </h3>
                    <Form.Group>
                        <Form.Label> Grad </Form.Label>
                        <Form.Control as="select" name="grad" onChange={this.onChange} value={this.state.grad}>
                            {this.props.gradovi.map(grad => (
                                <option id={grad.id}>{grad.naziv}</option>
                            ))}
                        </Form.Control>

                        <Form.Label> Dom </Form.Label>
                        <Form.Control as="select" name="dom" onChange={this.onChange} value={this.state.dom}>
                            {this.state.domovi.map(dom => (
                                <option id={dom.id}>{dom.naziv}</option>
                            ))}
                        </Form.Control>
                    </Form.Group>

                    <Form.Group>
                        <Form.Row>
                            <Col xs={9}>
                                <Form.Label> Paviljon </Form.Label>
                                <Form.Control as="select" name="paviljon" onChange={this.onSelectPaviljon}
                                              value={this.state.paviljon !== undefined && this.state.paviljon.naziv}>
                                    {
                                        this.props.gradovi.filter(grad =>
                                            grad.naziv === this.state.grad
                                        ).map(grad =>
                                            grad.domovi.filter(
                                                dom => dom.naziv === this.state.dom
                                            ).map(dom =>
                                                dom.paviljoni.map(paviljon => (
                                                    <option id={paviljon.id}>{paviljon.naziv}</option>
                                                ))
                                            ))
                                    }
                                </Form.Control>
                            </Col>
                            <Col>
                                <Form.Group>
                                    <Form.Label> Kat </Form.Label>
                                    <Form.Control name="kat" type="number" min={0}
                                                  max={this.state.paviljon === undefined ? 0 : this.state.paviljon.brojKatova}
                                                  disabled={this.state.paviljon === undefined}
                                                  defaultValue={this.state.kat}/>
                                </Form.Group>
                            </Col>
                        </Form.Row>
                    </Form.Group>


                    <Form.Group>
                        <Form.Row>
                            <Col>
                                <Form.Label> Broj kreveta </Form.Label>
                                <Form.Control as="select" name="brojKreveta"
                                              onChange={this.onChange} value={this.capitalize(this.state.brojKreveta)}>
                                    <option>Jednokrevetna</option>
                                    <option>Dvokrevetna</option>
                                    <option>Trokrevetna</option>
                                    <option>Višekrevetna</option>
                                </Form.Control>
                            </Col>
                            <Col>
                                <Form.Label> Tip kupaonice </Form.Label>
                                <Form.Control as="select" name="tipKupaonice"
                                              value={this.capitalize(this.state.tipKupaonice)}
                                              onChange={this.onChange}>
                                    <option> Privatna</option>
                                    <option> Dijeljena</option>
                                </Form.Control>
                            </Col>
                        </Form.Row>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> Komentar </Form.Label>
                        <Form.Control name="komentar" as="textarea" rows="3" onChange={this.onChange}
                                      defaultValue={this.state.komentar}/>
                    </Form.Group>

                    <Button type="submit" variant="dark" size="lg" block> Spremi promjene </Button>
                </Form>
            </div>
        )
    }
}

export default Soba;