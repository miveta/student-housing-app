import React, {Component} from "react";
import {Button, Col, Form} from 'react-bootstrap';
import cookie from "react-cookies";

class Soba extends Component {
    user;
    grad = this.props.grad

    constructor(props) {
        super(props);
        this.state = {
            paviljoni: [],
            grad: '',
            dom: '',
            id: '',
            brojKreveta: 'Jednokrevetna',
            kat: '',
            komentar: '',
            paviljon: undefined,
            tipKupaonice: 'Privatna'
        };


        this.user = cookie.load('principal');
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }


    capitalize = (s) => {
        s = s.toLowerCase()
        return s.charAt(0).toUpperCase() + s.slice(1)
    }

    onChange = (event) => {
        let self = this
        const {name, value} = event.target;

        self.setState(state => ({...state, [name]: value, change: true}))

        if (name === "dom") {
            let dom = self.props.grad.domovi.filter(d => d.naziv === value)[0]
            self.setState({paviljon: dom.paviljoni[0]})
            self.setState({paviljoni: dom.paviljoni})
        } else if (name == "paviljon") {
            let paviljon = self.state.paviljoni.filter(p => p.naziv === value)[0]
            self.setState({paviljon: paviljon})
        }
    }

    onSubmit(e) {
        e.preventDefault();
        let self = this

        let soba = {
            idPaviljon: self.state.paviljon.id,
            kat: self.state.kat,
            brojKreveta: self.state.brojKreveta.toUpperCase(),
            tipKupaonice: self.state.tipKupaonice.toUpperCase(),
            komentar: self.state.komentar
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


        fetch(`${process.env.REACT_APP_BACKEND_URL}/soba/student?student_username=${this.user.korisnickoIme}`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                }
            }).then(json => {
            let dom = self.props.grad.domovi.filter(d => d.paviljoni.filter(p => p.id === json.paviljon.id).length !== 0)[0]
            self.setState({dom: dom.naziv, paviljoni: dom.paviljoni})
            self.setState({...json})
        }).catch(e => console.log(e))
    }

    // todo vrati ono gdje je submit disabled dok god se ne naprave promjene
    render() {
        if (this.props.grad.domovi.length !== 0) {
            let dom = this.props.grad.domovi[0]

            if (this.state.dom === '' && this.state.paviljon === undefined) {
                this.setState({dom: dom.naziv})
                this.setState({paviljoni: dom.paviljoni})
            }

            if (this.state.paviljon === undefined && this.state.dom !== "") {
                let pav = this.props.grad.domovi.filter(d => d.naziv === this.state.dom)[0].paviljoni[0]
                if (pav !== undefined) {
                    this.setState({paviljon: pav})
                }
            }
        }


        return (

            <Form onSubmit={this.onSubmit} className={"innerForm"}>
                <h3> Nudim sobu </h3>
                <Form.Group>
                    <Form.Label> Dom </Form.Label>
                    <Form.Control as="select" name="dom" onChange={this.onChange} value={this.state.dom}>
                        {this.props.grad.domovi.map(dom => (
                            <option id={dom.id}>{dom.naziv}</option>
                        ))}
                    </Form.Control>
                </Form.Group>

                <Form.Group>
                    <Form.Row>
                        <Col xs={9}>
                            <Form.Label> Paviljon </Form.Label>
                            <Form.Control as="select" name="paviljon" onChange={this.onChange}
                                          value={this.state.paviljon && this.state.paviljon.naziv}>
                                {

                                    this.props.grad.domovi.filter(
                                        dom => dom.naziv === this.state.dom
                                    ).map(dom =>
                                        dom.paviljoni.map(paviljon => (
                                            <option id={paviljon.id}>{paviljon.naziv}</option>
                                        ))
                                    )
                                }
                            </Form.Control>
                            {this.state.paviljon !== undefined && this.state.paviljon.kategorija !== null &&
                            <Form.Text className={"textMuted"}>
                                Ovo je paviljon {this.state.paviljon.kategorija} kategorije.
                            </Form.Text>
                            }
                        </Col>
                        <Col>
                            <Form.Group>
                                <Form.Label> Kat </Form.Label>
                                <Form.Control name="kat" type="number" min={0}
                                              onChange={this.onChange}
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

        )
    }
}

export default Soba;