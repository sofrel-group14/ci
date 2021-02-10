import React, { useEffect, useState } from 'react'
import Moment from 'react-moment';
import Spinner from './spinner.gif'
import Github from './githublight.png'

import './Expandable.css'

/**
 * React app written by Axel Elmarsson
 */
const App = () => {

    const [builds, setBuilds] = useState([])
    const [fetching, setFetching] = useState(true)
    const [error, setError] = useState("")

    useEffect(_ => {
        // Used when error on fetch
        let response

        fetch("http://localhost:8080/logs")
        .then(res => {
            response = res
            return res.json()
        })
        .then(json => {
            console.log(json)
            // Sort data, latest first
            json = json.sort((a, b) => {
                let ad = new Date(a.timestamp)
                let bd = new Date(b.timestamp)
                if (ad < bd) return 1
                else if (ad > bd) return -1
                return 0
            })
            setBuilds(json)
            setFetching(false)
            
        })
        .catch(err => {
            setFetching(false)
            setError(`${response.status}: ${response.statusText}`)
        })
    }, [true])

  return (
    <div className="pretty">
        <h1>Build logs</h1>
        {!fetching && <h3>Showing {builds.length} logs</h3>}
        {fetching ?
            <img src={Spinner} />
            :
            (error.length != 0 ?
                <div style={{color: "red"}}>{error}</div>
                :
                builds.map(x => <Expandable {...x} key={x._id}/>)    
            )
        }
        <footer>
            <p>
                {/* Ordered by lastname A-Z */}
                Written by <a href="https://github.com/elmaxe" target="_blank" rel="noopener noreferrer">Axel Elmarsson</a>
                , <a href="#" target="_blank" rel="noopener noreferrer">Telo Johar</a>,
                , <a href="https://github.com/andreaskth" target="_blank" rel="noopener noreferrer">Andreas Kärrby</a>
                , <a href="https://github.com/K2017" target="_blank" rel="noopener noreferrer">Yannis Paetzelt</a>{" "}
                and <a href="#" target="_blank" rel="noopener noreferrer">Adeeb Syed Shah</a> for
                the course <a href="https://www.kth.se/student/kurser/kurs/DD2480" target="_blank" rel="noopener noreferrer"><i>DD2480 Software Engineering Fundamentals</i></a> in 2021</p>
            <p><a href="https://github.com/sofrel-group14/ci" target="_blank" rel="noopener noreferrer"><img src={Github} style={{width: "20px"}}/></a></p>
        </footer>
    </div>
  );
}

// Component used to render each log
const Expandable = ({timestamp = "", buildSuccess, commitHash = "", buildResults = ""}) => {

    const [open, toggleOpen] = useState(false)

    return (
    <div className="Expandable" >
        <div className="bar">
            <span className="arrow" onClick={_ => toggleOpen(!open)} style={{cursor: "pointer"}}>
                <i className="fas fa-chevron-down" style={open ? {transform: "rotate(180deg)"} : {}}></i>
            </span>
            <span className="time" onClick={_ => toggleOpen(!open)} style={{cursor: "pointer"}}>
                <Moment format="DD MMM YYYY kk:mm:ss">{timestamp}</Moment>
            </span>
            <span>
                <a
                    href={(`https://github.com/sofrel-group14/ci/commit/${commitHash}`) || "https://github.com/sofrel-group14/ci"}
                    target="_blank"
                    rel="noopener noreferrer"
                    title={commitHash ? (`https://github.com/sofrel-group14/ci/commit/${commitHash}`) : ""}
                >
                    {commitHash && commitHash.substring(0, 7)}
                </a>
            </span>
            <span>
            {buildSuccess == true ?
                <i className="fas fa-check" style={{color: "green"}} title="Success"></i>
                :
                <i className="fas fa-times" style={{color: "red"}} title="Failure"></i>
            }
            </span>
        </div>
        <div className="content" style={open ? {} : {display: "none"}}>
            {buildResults}
        </div>
    </div>
    )
}

export default App;
