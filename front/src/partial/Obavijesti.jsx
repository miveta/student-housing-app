import React, {Component} from 'react';
import Dropdown from "react-bootstrap/Dropdown";
import DropdownToggle from "react-bootstrap/DropdownToggle";
import DropdownMenu from "react-bootstrap/DropdownMenu";
import {LinkContainer} from 'react-router-bootstrap'

class Obavijesti extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page: 0,
            size: 5,
            obavijesti: []
        }

    }

    getObavijesti = () => {
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(body)
        };


        fetch(`${process.env.REACT_APP_BACKEND_URL}/obavijesti`, options)
            .then(response => {
                console.log(response)
            }).catch(error => console.log(error));
    };

    render() {
        return (
            <Badge badgeContent={this.state.obavijesti.filter(obavijest => obavijest.novo === true).length}>
                <Dropdown alignRight>
                    <DropdownToggle id="dropdown-basic" variant="info">
                        <i className={"fa fa-bell"}/>
                    </DropdownToggle>
                    <DropdownMenu>
                        {
                            this.state.obavijesti.length > 0 ?
                                <>
                                    {
                                        this.state.obavijesti.map((row, idx) => (
                                            <LinkContainer to={row.url || "/"}
                                                           key={idx}
                                                           style={{'borderLeft': row.novo ? '10px solid #33B5E7' : ''}}>
                                                <Dropdown.Item href=row.url>

                                                </Dropdown.Item>
                                            </LinkContainer>
                                        ))
                                    }
                                </>
                                :
                                <Alert variant="info">Trenutno nemate obavijesti!</Alert>
                        }
                    </DropdownMenu>
                </Dropdown>

            </Badge>
        )
    }
}

export default Obavijesti;