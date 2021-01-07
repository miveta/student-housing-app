import React, {Component} from 'react';
import OglasList from "../components/OglasList";

class Homepage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            oglasi: []
        };
    }

    componentDidMount() {
        const options = {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`${process.env.REACT_APP_BACKEND_URL}/oglas/list`, options)
            .then(response => {
                if (response.status === 200) {
                    response.json().then(body =>
                        this.setState({oglasi: body})
                    ).catch(error => console.log(error))
                }
            });
    }

    render() {
        return (
            <div className="middle">
                <h2>Oglasi</h2>
                <OglasList oglasi={this.state.oglasi} isLoggedIn={this.props.isLoggedIn}/>
            </div>
        )
    }
}

export default Homepage;