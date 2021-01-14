import React, {Component} from "react";
import ZamjenaCard from "./ZamjenaCard";

class ZamjenaList extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                {this.props.upiti !== undefined && this.props.upiti.length !== 0 &&
                this.props.upiti.map(upit => (
                    <div className={"innerForm"}>
                        <ZamjenaCard key={upit.id}
                                     par={upit}/>
                    </div>
                ))
                }
            </div>
        );
    }
}


export default ZamjenaList;