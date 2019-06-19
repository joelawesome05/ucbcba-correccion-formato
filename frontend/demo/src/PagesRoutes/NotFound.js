import React, { Component } from 'react';
import "../style/UploadFile.css";
import ucbImage from '../images/ucbcba.png';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faRedo } from '@fortawesome/free-solid-svg-icons'
import { Link } from 'react-router-dom';

class NotFound extends Component {

    componentDidMount() {
        document.body.style = 'background: none;';
        document.body.style = 'background-image: ../images/pattern_news.png;';
    }


    render() {
        return (
            <div>
                <div className="mynavbar">
                    <center>
                        <Link to="/">
                            <img src={ucbImage} />
                        </Link>
                    </center>
                </div>
                <div className="container main">
                    <div className="row justify-content-md-center">
                        <div className="presentation">
                            <center>
                                <h5 className="alert alert-danger" role="alert">Página no encontrada </h5>
                                <Link to="/">
                                    <button type="button" className="btn btn-outline-primary btn-lg"> Regresar al inicio <FontAwesomeIcon icon={faRedo} /></button>
                                </Link>
                            </center>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default NotFound;
