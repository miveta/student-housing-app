import React, {Component} from 'react';
import ArhiviraniOglasCard from "./ArhiviraniOglasCard";

class ArhiviraniOglasiList extends Component {
    constructor(props) {
        super(props)
    }

    render() {
        return (
            <div>
                {
                    this.props.oglasi.map(oglas =>
                        <div className={"card"}>
                            <ArhiviraniOglasCard
                                id={oglas.id}
                                key={oglas.id}
                                oglas={oglas}/>
                        </div>
                    )
                }
            </div>

        );
    }
}

export default ArhiviraniOglasiList;
