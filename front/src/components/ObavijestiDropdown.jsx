import React, {Component} from 'react';
import cookie from 'react-cookies';
import {Alert, Dropdown} from 'react-bootstrap';
import {LinkContainer} from 'react-router-bootstrap'
import Badge from '@material-ui/core/Badge';
import DropdownToggle from 'react-bootstrap/DropdownToggle';
import DropdownMenu from 'react-bootstrap/DropdownMenu';
import Button from "react-bootstrap/Button";
import NotificationsIcon from '@material-ui/icons/Notifications';

class ObavijestiDropdown extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page: 0,
            size: 5,
            obavijesti: [],
            user: cookie.load('principal')
        };


        /* this.client = new Client();

        this.client.configure({
            brokerURL: `ws://${process.env.REACT_APP_BACKEND_URL}/stomp`,
            onConnect: () => {
                console.log('onConnect');


                this.client.subscribe('/topic/greetings', message => {
                    console.log("pozvao")
                    alert(message.body);
                });
            },
            // Helps during debugging, remove in production
            debug: (str) => {
                console.log(new Date(), str);
            }
        });

        this.client.activate();*/
    }


    componentDidMount() {

        /*        this.stompClient = Stomp.over(function () {
                    return new SockJS('/gs-guide-websocket');
                });

                console.log(this.stompClient)

                this.stompClient.reconnect_delay = 5000;
                this.stompClient.debug = () => {
                };

                this.stompClient.connect({}, () => {
                    console.log("connect")
                    this.stompClient.subscribe('/topic/greetings',
                        data => {
                            this.setState({obavijesti: JSON.parse(data.body)});
                        }
                    );

                    this.getObavijesti();

                    setInterval(() => this.getObavijesti(), 15000);
                }, error => {
                    console.log("STOMP error: " + error);
                });*/
    }

    /*getObavijesti = () => {
        this.stompClient.send("/obavijesti", {}, {});
    };*/

    onClickObavijest = (redak) => {
        if (redak.novo === true) {
            redak.novo = false;

            const options = {
                method: 'GET',
                headers: {
                    'Access-Control-Allow-Origin': '*'
                }
            };

            fetch(`${process.env.REACT_APP_BACKEND_URL}/user/obavijesti?obavijest_id=${redak.id}`, options)
                .then(response => {
                    console.log(response.status)
                }).catch(e => console.log(e))
        }

        this.getObavijesti();
    };

    markAsReadObavijesti = () => {
        let self = this;
        let idKorisnik = cookie.load('principal').id;
        /*axios.put(`/api/obavijesti/${idKorisnik}`
        ).then(function (response) {
            self.getObavijesti();
        }).catch(function (error) {
            console.log(error);
        });*/

    }

    componentWillUnmount() {
        this.stompClient.disconnect();
    };

    render() {
        return (
            <Badge color="secondary"
                   badgeContent={this.state.obavijesti.filter(
                       function (obavijest) {
                           return obavijest.novo === true;
                       }
                   ).length}>
                <Dropdown alignRight>
                    <DropdownToggle id="dropdown-basic" variant="light" size={"xs"}>
                        <NotificationsIcon fontSize={"small"}/>
                    </DropdownToggle>
                    <DropdownMenu>

                        {
                            this.state.obavijesti.length > 0 ?
                                <>
                                    {this.state.obavijesti.map((redak, index) => (
                                        <LinkContainer to={redak.url || "/"}
                                                       key={index}
                                                       activeClassName=""
                                                       style={{
                                                           'borderLeft': redak.novo ? '10px solid #33B5E7' : '',
                                                           /*'backgroundColor': redak.novo ? '#add8e6' : '',*/
                                                       }}>
                                            <Dropdown.Item href={redak.url}
                                                           onClick={() => this.onClickObavijest(redak)}>
                                                {redak.tekst}
                                            </Dropdown.Item>
                                        </LinkContainer>
                                    ))}

                                    <Button variant="info" block onClick={this.markAsReadObavijesti}>Označi sve kao
                                        pročitano.</Button>

                                </>
                                :
                                <Alert variant="info">Trenutno nemate obavijesti!</Alert>

                        }

                    </DropdownMenu>
                </Dropdown>
            </Badge>
        );
    }
}

export default ObavijestiDropdown;