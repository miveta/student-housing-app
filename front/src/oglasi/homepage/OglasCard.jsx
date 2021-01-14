import React, {Component} from "react";
import Lajkovi from "../../components/Lajkovi";
import * as cookie from "react-cookies";
import SobaReadOnly from "../SobaReadOnly";
import {withRouter} from "react-router-dom";

class OglasCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: cookie.load('principal')
        }
    }


    onClick = (e) => {
        console.log(e.target.className)
        console.log(e.target.className.includes("btn"))
        this.props.history.push(`/oglas/id=${this.props.oglas.id}`)
    }


    render() {
        let oglas = this.props.oglas;
        return (
            <div className={"Card"}>
                <SobaReadOnly soba={oglas.soba} title={oglas.student} onClick={this.onClick}/>

                <br/>
                <Lajkovi id="lajkovi" oglasId={oglas.id} user={this.state.user}/>

            </div>
        )
    }

}

export default withRouter(OglasCard);
