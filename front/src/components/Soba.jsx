import React, {Component} from "react";
import {Button, Form} from 'react-bootstrap';

class Soba extends Component {
    constructor(props) {
        super(props);
        this.state = {
            change: false,
            gradovi: [],
            izabrano: {
                grad: "",
                dom: "",
                paviljon: ""
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

        fetch(`${process.env.REACT_APP_BACKEND_URL}/soba/gradovi`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()

                } else {
                    console.log(response.status)
                }
            }).then(json => {
            this.setState({gradovi: json})
        })
    }

    // todo vrati ono gdje je submit disabled dok god se ne naprave promjene
    render() {
        return (
            <div className="middle">
                <Form onSubmit={this.onSubmit}>
                    <h3> Moja soba </h3>
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
                        <Form.Control as="select" name="dom"/>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> Kat </Form.Label>
                        <Form.Control name="kat" type="text"/>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> Broj kreveta </Form.Label>
                        <Form.Control as="select" defaultValue="Odaberi...">
                            <option> Jednokrevetna soba</option>
                            <option> Dvokrevetna soba</option>
                            <option> Trokrevetna soba</option>
                            <option> Višekrevetna soba</option>
                        </Form.Control>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> Tip kupaonice </Form.Label>
                        <Form.Control as="select" defaultValue="Odaberi...">
                            <option> U sobi</option>
                            <option> Zajednički</option>
                        </Form.Control>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> Komentar </Form.Label>
                        <Form.Control name="komentar" type="text"/>
                    </Form.Group>

                    <Button type="submit" variant="dark" size="lg" block> Spremi promjene </Button>
                </Form>
            </div>
        )
    }
}

export default Soba;