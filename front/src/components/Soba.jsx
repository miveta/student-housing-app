import React from "react";
import {Button, Form} from 'react-bootstrap';

function Soba(props) {

    function onSubmit(e) {
        e.preventDefault();
    }

    return (
        <Form onSubmit={onSubmit}>
            <h3> Moja soba </h3>
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
                <Form.Control as="select" defaultValue="Odaberi...">
                    <option> Jednokrevetna soba </option>
                    <option> Dvokrevetna soba </option>
                    <option> Trokrevetna soba </option>
                    <option> Višekrevetna soba </option>
                </Form.Control>
            </Form.Group>
            <Form.Group>
                <Form.Label> Tip kupaonice </Form.Label>
                <Form.Control as="select" defaultValue="Odaberi...">
                    <option> U sobi </option>
                    <option> Zajednički </option>
                </Form.Control>
            </Form.Group>
            <Form.Group>
                <Form.Label> Komentar </Form.Label>
                <Form.Control name="komentar" type="text"/>
            </Form.Group>
            <Button type="submit" variant="dark" size="lg" block> Predaj oglas </Button>
        </Form>
    )
}
export default Soba;