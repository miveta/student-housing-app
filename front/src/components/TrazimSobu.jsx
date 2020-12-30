import React, {Component} from "react";
import {Button, Form} from 'react-bootstrap';

class TrazimSobu extends Component{
    constructor(props) {
        super(props);
        this.state = {
            change: false,
            user: JSON.parse(localStorage.getItem("user")),
            grad: '' ,
            domovi: [],
            paviljoni: [],
            izabrano: {
                dom: [],
                paviljon: [],
                kat: [],
                brojKreveta: [],
                tipKupaonice: [],
                komentar: ''
            }
        };

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    onChange(event) {
        this.setState({change: true});

        console.log(event.target)
    }

    onSubmit(e) {
        e.preventDefault();
        const trazeniUvjeti = {
            grad: this.state.grad,
            dom: this.state.dom,
            paviljon: this.state.paviljon,
            kat: this.state.kat,
            brojKreveta: this.state.brojKreveta,
            tipKupaonice: this.state.tipKupaonice,
            komentar: this.state.komentar
        };
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(trazeniUvjeti)
        };

        return fetch(`${process.env.REACT_APP_BACKEND_URL}/uvjeti`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()

                } else {
                    console.log(response.status)
                }
            });

    }

    componentDidMount() {
        const options = {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/trazimSobu/grad`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()

                } else {
                    console.log(response.status)
                }
            }).then(json => {
            this.setState({grad: json})
        })
        fetch(`${process.env.REACT_APP_BACKEND_URL}/trazimSobu/domovi`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()

                } else {
                    console.log(response.status)
                }
            }).then(json => {
            this.setState({domovi: json})
        })
        fetch(`${process.env.REACT_APP_BACKEND_URL}/trazimSobu/paviljoni`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()

                } else {
                    console.log(response.status)
                }
            }).then(json => {
            this.setState({paviljoni: json})
        })
        fetch(`${process.env.REACT_APP_BACKEND_URL}/trazimSobu/katovi`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()

                } else {
                    console.log(response.status)
                }
            }).then(json => {
            this.setState({katovi: json})
        })
    }



    render(){
    return (

        <div className="middle">
            <Form onSubmit={this.onSubmit}>
                <h3> Tražim sobu </h3>
                <p>Grad: {this.state.grad}</p>
                <Form.Group>
                    <Form.Label> Dom </Form.Label>
                    {this.state.domovi.map(dom =>(
                        <Form.Check onChange={this.onChange} type="checkbox" id={dom.id} label={dom.naziv}/>
                    ))}
                </Form.Group>
                <Form.Group>
                    <Form.Label> Paviljon </Form.Label>
                    {this.state.paviljoni.map(paviljon =>(
                        <Form.Check onChange={this.onChange} type="checkbox" id={paviljon.id} label={paviljon.naziv}/>
                    ))}

                </Form.Group>
                <Form.Group>
                    <Form.Label> Kat </Form.Label>
                    <Form.Check  onChange={this.onChange} type="checkbox" label="1"/>
                    <Form.Check onChange={this.onChange} type="checkbox" label="2"/>
                    <Form.Check onChange={this.onChange} type="checkbox" label="3"/>
                    <Form.Check onChange={this.onChange} type="checkbox" label="4"/>
                    <Form.Check onChange={this.onChange} type="checkbox" label="Nebitno"/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Broj kreveta </Form.Label>
                    <Form.Check  onChange={this.onChange} type="checkbox" label="Jednokrevetna"/>
                    <Form.Check onChange={this.onChange} type="checkbox" label="Dvokrevetna"/>
                    <Form.Check onChange={this.onChange} type="checkbox" label="Trokrevetna"/>
                    <Form.Check onChange={this.onChange} type="checkbox" label="Višekrevetna"/>
                    <Form.Check onChange={this.onChange} type="checkbox" label="Nebitno"/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Tip kupaonice </Form.Label>
                    <Form.Check onChange={this.onChange}  type="checkbox" label="U sobi"  />
                    <Form.Check onChange={this.onChange}  type="checkbox" label="Zajednički"/>
                    <Form.Check onChange={this.onChange}  type="checkbox" label="Nebitno"/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Komentar </Form.Label>
                    <Form.Control name="komentar" type="text"  />
                </Form.Group>
                <Button type="submit" variant="dark" size="lg" block> Predaj oglas </Button>
            </Form>
        </div>
    )}
}
export default TrazimSobu;