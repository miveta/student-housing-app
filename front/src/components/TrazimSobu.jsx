import React, {Component, useEffect} from "react";
import { withRouter } from 'react-router-dom';

function TrazimSobu(props){
    const user = JSON.parse(localStorage.getItem("user"));
    const [grad, setGrad] = React.useState('');
    const [domovi, setDomovi] = React.useState([{id: '', imaMenzu: '', naziv: ''}]) ;

    const [uvjeti, setUvjeti] = React.useState({dom: [], paviljon: [], kat: [], tipKupaonice: [],brojKreveta: [], komentar: ''});
    const paviljoni = ['1', '2', '3', '4', '5', '6'];
    const katovi = ['1', '2', '3', '4'];
    const kreveti = [
        {name:"Jednokrevetna", value:"JEDNOKREVETNA"},
        {name:"Dvokrevetna", value:"DVOKREVETNA"},
        {name:"Trokrevetna", value:"JEDNOKREVETNA"},
        {name:"Nebitno", value:"NEBITNO"}
    ]
    const kupaonice = [
        {name:"Privatna", value:"PRIVATNA"},
        {name:"Zajednicka", value:"ZAJEDNICKA"},
        {name:"Nebitno", value:"NEBITNO"}
    ]
    function onChange(event){
        const {name, value} = event.target;
        const ids = uvjeti[name].map(el => el.id);
        if(event.target.checked){
            uvjeti[name].push(value);
        }else{
            const index = ids.indexOf(value.id);
            uvjeti[name].splice(index,1);
        }


    }


    function onSubmit(e) {
        e.preventDefault();
        const trazeniUvjeti = {
            grad: grad,
            dom: uvjeti["dom"],
            paviljon: uvjeti["paviljon"],
            kat: uvjeti["kat"],
            brojKreveta: uvjeti["brojKreveta"],
            tipKupaonice: uvjeti["tipKupaonice"],
            komentar: uvjeti["komentar"]
        };
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(trazeniUvjeti)
        };

        return fetch(`http://localhost:8080/trazimSobu/uvjeti/?user=${user.korisnickoIme}&domovi=
        ${uvjeti["dom"]}&paviljoni=${uvjeti["paviljon"]}
        &katovi=${uvjeti["kat"]}&brojKreveta=${uvjeti["brojKreveta"]}&tipKupaonice=${uvjeti["tipKupaonice"]}&komentar=${uvjeti["komentar"]}`, options)
            .then(response => {
                if (response.status === 200) {
                    response.json().then(() => props.history.push('/'));
                }
                else {
                    console.log(response.status)
                }
            });

    }

    useEffect(() => {
        const options = {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };

        fetch(`http://localhost:8080/trazimSobu/grad?user=${user.korisnickoIme}`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()

                } else {
                    console.log(response.status)
                }
            }).then(json => {
            setGrad(json);
        }).then(fetch(`http://localhost:8080/trazimSobu/domovi`, options)
            .then(response => {
                if (response.status === 200) {
                    return response.json()

                } else {
                    console.log(response.status)
                }
            }).then(json => {
                setDomovi(json)
            }))
        //     .then(fetch(`http://localhost:8080/trazimSobu/paviljoni`, options)
        //     .then(response => {
        //         if (response.status === 200) {
        //             return response.json()
        //
        //         } else {
        //             console.log(response.status)
        //         }
        //     }).then(json => {
        //     this.setState({katovi: json})
        // }))
    },[]);




    return (

        <div className="middle" >

            <h3>Grad: {grad.naziv}</h3>

            <form onSubmit={onSubmit}>
                <label>
                    Dom:

                    <br/>
                    {domovi.map(dom =>(
                        <li>
                        <label>
                            <input
                                type="checkbox"
                                onChange={onChange}
                                value={dom.id}
                                name="dom"
                        />{dom.naziv}
                        </label>
                        </li>
                    ))}
                </label>
                <br/>
                <label>
                    Paviljon:

                    <br/>
                    {paviljoni.map(p =>(
                        <li>
                            <label>
                                <input
                                    type="checkbox"
                                    onChange={onChange}
                                    value={p}
                                    name="paviljon"
                                />{p}
                            </label>
                        </li>
                    ))}
                </label>

                <br/>
                <label>
                    Kat:

                    <br/>
                    {katovi.map(p =>(
                        <li>
                            <label>
                                <input
                                    type="checkbox"
                                    onChange={onChange}
                                    value={p}
                                    name="kat"
                                />{p}
                            </label>
                        </li>
                    ))}
                </label>

                <br/>
                <label>
                    Broj kreveta:

                    <br/>
                    {kreveti.map(p =>(
                        <li>
                            <label>
                                <input
                                    type="checkbox"
                                    onChange={onChange}
                                    value={p.value}
                                    name="brojKreveta"
                                />{p.name}
                            </label>
                        </li>
                    ))}
                </label>
                <br/>
                <label>
                    Tip kupaonice:

                    <br/>
                    {kupaonice.map(p =>(
                        <li>
                            <label>
                                <input
                                    type="checkbox"
                                    onChange={onChange}
                                    value={p.value}
                                    name="tipKupaonice"
                                />{p.name}
                            </label>
                        </li>
                    ))}
                </label>
                <br/>
                <label>
                    Komentar:

                    <br/>
                    <input
                        type="text"
                        name="komentar"
                        onChange={onChange}
                    />

                </label>
                <br/>
                <input type="submit"  value="Submit" />

            </form>

        </div>

    )
}
export default withRouter(TrazimSobu);