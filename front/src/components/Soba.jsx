import React, {Component} from "react";
import {Button, Form} from 'react-bootstrap';

class Soba extends Component {
    constructor(props) {
        super(props);
        this.state = {
            change: false,
            gradovi: [],
            domovi: [],
            paviljoni: []
        };

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.onClick = this.onClick.bind(this);
    }

    onSelectGrad = (event) => {
        let imeGrada = event.target.value;

        let grad = this.state.gradovi.filter(g => g.naziv === imeGrada)[0];

        this.setState({
            domovi: grad.domovi
        })
    }

    /*    onSelectDom = (event) => {
            let imeDoma = event.target.value;

            let dom = this.state.gradovi.filter(d => d.naziv === imeDoma)[0];

            this.setState({
                paviljon: dom.
            })
        }*/

    onChange(event) {
        this.setState({change: true});

        console.log(event.target)
    }

    onClick(event) {
        const {name, value} = event.target;
        this.setState({[name]: value})
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
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> Dom </Form.Label>
                        <Form.Control as="select" name="dom" onChange={this.onClick}
                                      disabled={this.state.domovi.length === 0}>
                            {
                                this.state.domovi.map(dom => (
                                    <option id={dom.id}>{dom.naziv}</option>
                                ))
                            }
                        </Form.Control>
                    </Form.Group>
                    {/*    */} <Form.Group>
                    <Form.Label> Paviljon </Form.Label>
                    <Form.Control as="select" name="dom" onChange={this.onClick}
                                  disabled={this.state.paviljoni.length === 0}>
                        {
                            this.state.paviljoni.map(paviljon => (

                                <option id={paviljon.id}>{paviljon.naziv}</option>
                            ))
                        }
                    </Form.Control>
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