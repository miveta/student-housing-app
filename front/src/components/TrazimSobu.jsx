import React, {Component} from "react";
import {Button, Form} from 'react-bootstrap';

class TrazimSobu extends Component{
    constructor(props) {
        super(props);
        this.state = {
            change: false,
            gradovi: [],
            domovi: [],
            paviljoni: [],
            katovi: [],
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
                <Form.Group>
                    <Form.Label> Grad </Form.Label>
                    <Form.Control as="select" defaultValue="Odaberi...">
                        {this.state.gradovi.map(grad => (
                            <option id={grad.id}>{grad.naziv}</option>
                        ))}
                    </Form.Control>

                </Form.Group>
                <Form.Group>
                    <Form.Label> Dom </Form.Label>
                    <Form.Check>{this.state.domovi.map(dom =>(
                        <option id={dom.id}>{dom.naziv}</option>
                    ))}</Form.Check>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Paviljon </Form.Label>
                    <Form.Check>{this.state.paviljoni.map(paviljon =>(
                        <option id={paviljon.id}>{paviljon.naziv}</option>
                    ))}</Form.Check>

                </Form.Group>
                <Form.Group>
                    <Form.Label> Kat </Form.Label>
                    <Form.Check>{this.state.katovi.map(kat =>(
                        <option id={kat.id}>{kat.naziv}</option>
                    ))}</Form.Check>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Broj kreveta </Form.Label>
                    <Form.Check type="checkbox" label="Jednokrevetna soba"  />
                    <Form.Check type="checkbox" label="Dvokrevetna soba"/>
                    <Form.Check type="checkbox" label="Trokrevetna soba" />
                    <Form.Check type="checkbox" label="Višekrevetna soba"  />
                    <Form.Check type="checkbox" label="Nebitno"/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Tip kupaonice </Form.Label>
                    <Form.Check type="checkbox" label="U sobi"  />
                    <Form.Check type="checkbox" label="Zajednički"/>
                    <Form.Check type="checkbox" label="Nebitno"/>
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