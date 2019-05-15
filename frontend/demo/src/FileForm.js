import React, { Component } from 'react';
import "./style/FileForm.css";
import ucbImage from './images/ucbcba.png';
import { Link } from 'react-router-dom';

class FileForm extends Component {

    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.uploadSingleFile = this.uploadSingleFile.bind(this);
        this.fileInput = React.createRef();
    }

    state = {
        status: '',
        error: ''
    }

    componentDidMount() {
        document.body.style = 'background: none;';
        document.body.style = 'background-image: ./images/pattern_news.png;';
    }

    handleSubmit(event) {
        this.setState({ status: '' });
        this.setState({ error: '' });
        this.uploadSingleFile(this.fileInput.current.files[0]);
    }

    uploadSingleFile(file) {
        var formData = new FormData();
        formData.append("file", file);
        this.setState({ status: 'Cargando archivo...' })
        fetch('/api/uploadFile', {
            method: 'POST',
            body: formData
        }).then(
            response => {
                this.setState({ status: '' });
                return response.json();
            }
        ).then(
            data => {
                console.log(data);
                if (data.status) {
                    throw new Error((data && data.message) || "Ocurrió algún error.");
                } else {
                    this.props.history.push(`/calibrarPaginas/${encodeURI(data.fileName)}`);
                }

            }
        ).catch(
            error => {
                this.setState({ error: error.message });
            }
        );

    }

    render() {
        var messageCode = null;
        if (this.state.status !== '') {
            messageCode = (<div className="col">
                <center>
                    <p className="alert alert-info myAlert" role="alert"> {this.state.status} </p>
                </center>
            </div>);
        }
        var errorCode = null;
        if (this.state.error !== '') {
            messageCode = (<div className="col">
                <center>
                    <p className="alert alert-danger myAlert" role="alert"> {this.state.error} </p>
                </center>
            </div>);
        }
        return (
            <div >
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
                                <h3 className="text-guide-1"> Revisa el formato de tu trabajo académico ¡aquí!</h3>
                                <h4 className="text-guide-2"> Sube tu documento en formato PDF por favor</h4>
                            </center>
                        </div>
                    </div>
                    <div className="row justify-content-end">
                        <div className="col-4">
                            <center>
                                <button className="btn btn-primary btn-lg btn-file">
                                    Seleccione su archivo PDF
                                    <input type="file" name="file" accept="application/pdf" required ref={this.fileInput} onChange={this.handleSubmit} />
                                </button>
                                {messageCode}
                                {errorCode}
                            </center>
                        </div>
                        <div className="col-4">
                            <div className="guia">
                                <a target="_blank" href="https://siaa.ucbcba.edu.bo/siaa2/Documentos/Modelos/GUIAUCBPTA2013.pdf">Consulte la guía aquí
                                    <img className="guiaImage" align="right" src={require('./images/guia.png')} />
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="container main">
                    <h5 className="textTips"> Consejos y recomendaciones</h5>
                    <div className="card-deck">
                        <div className="card">
                            <div className="card-body">
                                <h5 className="card-title">Carátula</h5>
                                <p className="card-text cardText">Recuerda agregar sangría negativa a la primera línea de la carátula para que esté entre los márgenes establecidos según la guía.</p>
                            </div>
                            <img className="card-img-top tipsCard" src={require('./images/caratula.png')} />
                            <div className="card-footer">
                                <small className="text-muted">Consejo</small>
                            </div>
                        </div>
                        <div className="card">
                            <div className="card-body">
                                <h5 className="card-title">Bibliografía</h5>
                                <p className="card-text cardText">Recuerda agregar un espaciado entre párrafos para que el sistema garantice una correcta revisión del formato en la bibliografía.</p>
                            </div>
                            <img className="card-img-top tipsCard" src={require('./images/bibliografia.png')} />
                            <div className="card-footer">
                                <small className="text-muted">Recomendación</small>
                            </div>
                        </div>
                        <div className="card">
                            <div className="card-body">
                                <h5 className="card-title">Advertencias</h5>
                                <p className="card-text cardText">Recuerda que las advertencias son sólo posibles errores de incuplimiento en el formato. Usar un citerio adecuado ante las advertencias.</p>
                            </div>
                            <img className="card-img-top tipsCard" src={require('./images/advertencias.png')} />
                            <div className="card-footer">
                                <small className="text-muted">Recomendación</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default FileForm;
