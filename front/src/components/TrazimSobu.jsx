import React from "react";
import {Button, Form} from 'react-bootstrap';

function TrazimSobu(props) {

    function onSubmit(e) {
        e.preventDefault();
    }

    return (
        <div className="middle">
            <Form onSubmit={onSubmit}>
                <h3> Tražim sobu </h3>
                <Form.Group>
                    <Form.Label> Grad </Form.Label>
                    <Form.Control as="select" defaultValue="Odaberi...">
                        <option> Zagreb </option>
                        <option> Split </option>
                        <option> Zadar </option>
                        <option> Rijeka </option>
                    </Form.Control>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Dom </Form.Label>
                    <Form.Control name="dom" type="text"/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Kat </Form.Label>
                    <Form.Control name="kat" type="text"/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Broj kreveta </Form.Label>
                    <Form.Check type="checkbox" label="Jednokrevetna soba" />
                    <Form.Check type="checkbox" label="Dvokrevetna soba" />
                    <Form.Check type="checkbox" label="Trokrevetna soba" />
                    <Form.Check type="checkbox" label="Višekrevetna soba" />
                </Form.Group>
                <Form.Group>
                    <Form.Label> Tip kupaonice </Form.Label>
                    <Form.Check type="checkbox" label="U sobi" />
                    <Form.Check type="checkbox" label="Zajednički" />
                </Form.Group>
                <Form.Group>
                    <Form.Label> Komentar </Form.Label>
                    <Form.Control name="komentar" type="text"/>
                </Form.Group>
                <Button type="submit" variant="dark" size="lg" block> Predaj oglas </Button>
            </Form>
        </div>
    )
}
export default TrazimSobu;