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

        fetch("/logs")
        .then(res => {
            response = res
            return res.json()
        })
        .then(json => {
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
    <div className="pretty" style={{marginBottom: "100px"}}>
        <h1>Build logs</h1>
        {!fetching && <h3>Showing {builds.length} logs</h3>}
        {fetching ?
            <img src={Spinner} />
            :
            (error.length != 0 ?
                <div style={{color: "red"}}>{error}</div>
                :
                builds.map((x,i) => {

                    return (
                        <Expandable id={x.commitHash} {...x} key={x._id} startOpen={window.location.hash.split("#")[1] === x.commitHash ? true : false}/>
                    )
                })
            )
        }
        <footer>
            <p>
                {/* Ordered by lastname A-Z */}
                Written by <a href="https://github.com/elmaxe" target="_blank" rel="noopener noreferrer">Axel Elmarsson</a>
                , <a href="https://github.com/teljoh" target="_blank" rel="noopener noreferrer">Telo Johar</a>
                , <a href="https://github.com/andreaskth" target="_blank" rel="noopener noreferrer">Andreas Kärrby</a>
                , <a href="https://github.com/K2017" target="_blank" rel="noopener noreferrer">Yannis Paetzelt</a>{" "}
                and <a href="https://github.com/Internet-Person-IP " target="_blank" rel="noopener noreferrer">Adeeb Syed Shah</a> for
                the course <a href="https://www.kth.se/student/kurser/kurs/DD2480" target="_blank" rel="noopener noreferrer"><i>DD2480 Software Engineering Fundamentals</i></a> in 2021</p>
            <p><a href="https://github.com/sofrel-group14/ci" target="_blank" rel="noopener noreferrer"><img src={Github} style={{width: "20px"}}/></a></p>
        </footer>
    </div>
  );
}

// Component used to render each log
const Expandable = ({timestamp = "", buildSuccess, commitHash = "", buildResults = "", startOpen = false, id, branchName = ""}) => {
    const [open, toggleOpen] = useState(startOpen)

    const scroll = _ => {
        let elem = document.getElementById(id);
        elem.scrollIntoView();
    }

    useEffect(_ => {
        scroll()
    }, [window.location.hash])

    return (
    <div className="Expandable" onClick={_ => toggleOpen(!open)} style={{cursor: "pointer"}} id={id}>
        <div className="bar">
            <span className="arrow">
                <i className="fas fa-chevron-down" style={open ? {transform: "rotate(180deg)"} : {}}></i>
            </span>
            <span className="time" >
                <Moment format="DD MMM YYYY kk:mm:ss">{timestamp}</Moment>
            </span>
            <span>
                <a
                    href={(`https://github.com/sofrel-group14/ci/commit/${commitHash}`) || "https://github.com/sofrel-group14/ci"}
                    target="_blank"
                    rel="noopener noreferrer"
                    title={commitHash ? (`https://github.com/sofrel-group14/ci/commit/${commitHash}`) : ""}
                    onClick={e => e.stopPropagation()}
                >
                    {commitHash && commitHash.substring(0, 7)}
                </a>
            </span>
            <span>
                {branchName}
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
