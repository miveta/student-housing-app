import React, {Component} from "react";
import ZamjenaCard from "./ZamjenaCard";

class ZamjenaList extends Component{
    constructor(props) {
        super(props);
        console.log(props)
    }

    render() {
        console.log(this.props)
        let myComponent;
        if(this.props.upiti !== undefined && this.props.upiti.length !== 0) {
            console.log("beep")
            myComponent = this.props.upiti.map(upit =>
                <div className={"innerForm"}>
                    <ZamjenaCard key={upit.id}
                                 par={upit}/>
                </div>
                )
        } else {
            myComponent = null
        }
        return (
            <div>
                {myComponent}
            </div>
        /*return (
            <div>
                {myComponent}
            </div>
            <div>

                this.props. != undefined && {
                    this.props.upiti.map(upit =>
                        <ZamjenaCard key={upit.id}
                                     oglas1={upit.oglas1}
                                     oglas2={upit.oglas2}/>
                    )
                }
            </div>*/
        );
    }
}
/*function ZamjenaList(props){
    console.log(props)
    return (
        <div>
            {
                props.upiti != undefined && props.upiti.map(upit =>
                    <ZamjenaCard key={upit.id}
                               oglas1={upit.oglas1}
                               oglas2={upit.oglas2}/>
                )
            }
        </div>

    );
}*/

export default ZamjenaList;