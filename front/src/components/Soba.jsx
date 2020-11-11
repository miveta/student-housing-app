import React, {Component} from "react";
import {Button, Form} from 'react-bootstrap';

class Soba extends Component {
    constructor(props) {
        super(props);
        this.state = {
            change: false
        };

        this.onChange = this.onChange.bind(this)

    }

    onChange(event) {
        this.setState({change: true});

        console.log(event.target)
        //setLoginForm(oldForm => ({...oldForm, [name]: value}))
    }

    onSubmit(e) {
        e.preventDefault();
    }

    render() {
        return (
            <div className="middle">
                <Form onSubmit={this.onSubmit}>
                    <h3> Moja soba </h3>
                    <Form.Group>
                        <Form.Label> Grad </Form.Label>
                        <Form.Control as="select" defaultValue="Odaberi..." onChange={this.onChange}>
                            <option> Zagreb</option>
                            <option> Split</option>
                            <option> Zadar</option>
                            <option> Rijeka</option>
                        </Form.Control>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> Dom </Form.Label>
                        <Form.Control name="dom" type="text" onChange={this.onChange}/>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> Kat </Form.Label>
                        <Form.Control name="kat" type="text" onChange={this.onChange}/>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> Broj kreveta </Form.Label>
                        <Form.Control as="select" defaultValue="Odaberi..." onChange={this.onChange}>
                            <option> Jednokrevetna soba</option>
                            <option> Dvokrevetna soba</option>
                            <option> Trokrevetna soba</option>
                            <option> Višekrevetna soba</option>
                        </Form.Control>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> Tip kupaonice </Form.Label>
                        <Form.Control as="select" defaultValue="Odaberi..." onChange={this.onChange}>
                            <option> U sobi</option>
                            <option> Zajednički</option>
                        </Form.Control>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> Komentar </Form.Label>
                        <Form.Control name="komentar" type="text" onChange={this.onChange}/>
                    </Form.Group>
                    <Button type="submit" variant="dark" size="lg" block disabled={!this.state.change}> Spremi
                        promjene </Button>
                </Form>
            </div>
        )
    }
}

export default Soba;