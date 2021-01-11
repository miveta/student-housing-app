import React from "react";
import ZamjenaCard from "./ZamjenaCard";

function ZamjenaList(props){
    console.log(props)
    return (
        <div>
            {
                props.upiti != undefined && props.upiti.map(upit =>
                    <ZamjenaCard key={upit.id}
                               oglas1Id={upit.oglas1ID}
                               oglas2Id={upit.oglas2ID}/>
                )
            }
        </div>

    );
}

export default ZamjenaList;