import React, {Component} from 'react';
import TrazimSobu from "../components/TrazimSobu";
import Soba from "../components/Soba";


class MojOglas extends Component {
    constructor(props) {
        super(props);

    }


    render() {
        return (
            <div className="middle">
                <Soba/>
                <TrazimSobu/>
            </div>
        )
    }
}


export default MojOglas;