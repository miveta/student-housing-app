import React from "react";
import {Button, Dropdown, Form} from 'react-bootstrap';

function NoviOglas(props) {

    function onSubmit(e) {
        e.preventDefault();
    }
    return (
        <Form onSubmit={onSubmit}>
            <h3>Predaj novi oglas</h3>
            <Form.Group>
                <Form.Label> Grad </Form.Label>
                <Form.Control name="grad" type="text"/>
            </Form.Group>
            <Form.Group>
                <Form.Label> Dom </Form.Label>
                <Form.Control name="dom" type="text"/>
            </Form.Group>
            <Form.Group>
                <Form.Label> Kat </Form.Label>
                <Form.Control name="kat" type="text"/>
            </Form.Group>
            <Dropdown>
                <Dropdown.Divider />
                <Dropdown.Toggle variant="outline-secondary" id="dropdown-basic"> Broj kreveta </Dropdown.Toggle>
                <Dropdown.Menu>
                    <Dropdown.Item> Jednokrevetna soba </Dropdown.Item>
                    <Dropdown.Item> Dvokrevetna soba </Dropdown.Item>
                    <Dropdown.Item> Trokrevetna soba </Dropdown.Item>
                    <Dropdown.Item> Višekrevetna soba </Dropdown.Item>
                </Dropdown.Menu>
                <Dropdown.Divider />
            </Dropdown>
            <Dropdown>
                <Dropdown.Toggle variant="outline-secondary" id="dropdown-basic"> Tip kupaonice </Dropdown.Toggle>
                <Dropdown.Menu>
                    <Dropdown.Item> U sobi </Dropdown.Item>
                    <Dropdown.Item> Zajednički </Dropdown.Item>
                </Dropdown.Menu>
                <Dropdown.Divider />
            </Dropdown>
            <Form.Group>
                <Form.Label> Komentar </Form.Label>
                <Form.Control name="komentar" type="text"/>
            </Form.Group>
            <Button type="submit" variant="dark" size="lg" block>Predaj oglas</Button>
        </Form>
    )
}
export default NoviOglas;