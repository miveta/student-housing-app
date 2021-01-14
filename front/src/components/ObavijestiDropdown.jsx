import React, {Component} from 'react';
import {Alert, Dropdown} from 'react-bootstrap';
import {LinkContainer} from 'react-router-bootstrap'
import Badge from '@material-ui/core/Badge';
import DropdownMenu from 'react-bootstrap/DropdownMenu';
import SockJsClient from "react-stomp";
import NotificationsIcon from '@material-ui/icons/Notifications';
import DropdownToggle from "react-bootstrap/DropdownToggle";
import * as cookie from "react-cookies";


class ObavijestiDropdown extends Component {
    constructor(props) {
        super(props)
        this.state = {
            obavijesti: [],
            user: cookie.load("principal"),

        }
    }


    getObavijesti = () => {
        try {
            this.clientRef.sendMessage('/obavijesti/user-all',
                this.props.user.korisnickoIme
            );
        } catch (e) {
            console.log(e)
        }

    }

    procitajObavijest = (redak) => {
        if (redak.procitana === false) {
            redak.procitana = true;

            const options = {
                method: 'POST',
                headers: {
                    'Access-Control-Allow-Origin': '*'
                }
            };

            fetch(`${process.env.REACT_APP_BACKEND_URL}/obavijesti/procitana?id=${redak.id}`, options)
                .then(response =>
                    this.getObavijesti()
                ).catch(error => console.log(error))
        }
    }

    procitajSveObavijesti = () => {
        this.state.obavijesti.map(redak => this.procitajObavijest(redak))
    }

    render() {

        return (
            <div>
                <SockJsClient url='http://localhost:8080/websocket-chat/'
                              topics={['/topic/user']}
                              onConnect={() => {
                                  this.getObavijesti()
                              }}
                              onDisconnect={() => {
                                  console.log("Disconnected");
                              }}
                              onMessage={(msg) => {
                                  this.setState({obavijesti: msg})
                              }}
                              ref={(client) => {
                                  this.clientRef = client
                              }}/>

                <Dropdown alignRight>
                    <DropdownToggle id="dropdown-basic" variant={"light"} drop={"left"} size={"sm"}>
                        <Badge color={"error"}
                               badgeContent={this.state.obavijesti.filter(
                                   function (obavijest) {
                                       return obavijest.procitana === false;
                                   }
                               ).length}>
                            <NotificationsIcon/>
                        </Badge>
                    </DropdownToggle>

                    <DropdownMenu>
                        {
                            this.state.obavijesti.length > 0 ?
                                <>
                                    {this.state.obavijesti.map((redak, index) => (
                                        <LinkContainer to={`/oglas/id=${redak.oglasId}` || "/"}
                                                       key={index}
                                                       activeClassName=""
                                                       style={{
                                                           'borderRight': !redak.procitana ? '10px solid #33B5E7' : '',
                                                           /*'backgroundColor': '#ffffff',*/
                                                       }}>
                                            <Dropdown.Item href={`/oglas/id=${redak.oglasId}`}
                                                           onClick={() => this.procitajObavijest(redak)}>
                                                {redak.tekst}
                                            </Dropdown.Item>
                                        </LinkContainer>
                                    ))}

                                    <Dropdown.Divider/>
                                    <Dropdown.Item onClick={this.procitajSveObavijesti}>
                                        Označi sve kao pročitano.
                                    </Dropdown.Item>
                                </>


                                :

                                <Alert variant="info">Trenutno nemate obavijesti!</Alert>

                        }

                    </DropdownMenu>
                </Dropdown>


            </div>


        )
    }


}

export default ObavijestiDropdown;
