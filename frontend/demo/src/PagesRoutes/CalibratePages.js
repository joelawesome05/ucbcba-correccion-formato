import React, { Component } from 'react';

import StepCover from "./wizards/StepCover"
import StepGeneralIndex from "./wizards/StepGeneralIndex"
import StepFigureIndex from "./wizards/StepFigureIndex"
import StepTableIndex from "./wizards/StepTableIndex"
import StepBiography from "./wizards/StepBibliography"
import StepAnnexed from "./wizards/StepAnnexed"
import { Link } from 'react-router-dom';
import ucbImage from '../images/ucbcba.png';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faRedo } from '@fortawesome/free-solid-svg-icons'
import "../style/CalibratePages.css";

var url = " ";
class CalibratePages extends Component {

    constructor(props) {
        super(props);
        this.state = {
            currentStep: 1,
            coverPage: 0,
            generalIndexStartPage: 0,
            generalIndexEndPage: 0,
            figureIndexStartPage: 0,
            figureIndexEndPage: 0,
            tableIndexStartPage: 0,
            tableIndexEndPage: 0,
            bibliographyStartPage: 0,
            bibliographyEndPage: 0,
            bibliograhyType: "UCB",
            annexesStartPage: 0,
            annexesEndPage: 0,
            totalPages: 1,
            noCoverPage: false,
            noGeneralIndex: false,
            noFigureIndex: false,
            noTableIndex: false,
            noBibliography: false,
            noAnnexes: false,
            isLoading: true,
            error: false,
            validInputs: true
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.nextStep = this.nextStep.bind(this)
        this.previousStep = this.previousStep.bind(this);
        this.getNumberbuttons = this.getNumberbuttons.bind(this);
        this.handleCoverPage = this.handleCoverPage.bind(this);
        this.handleGeneralIndex = this.handleGeneralIndex.bind(this);
        this.handleFigureIndex = this.handleFigureIndex.bind(this);
        this.handleTableIndex = this.handleTableIndex.bind(this);
        this.handleBibliography = this.handleBibliography.bind(this);
        this.handleAnnexes = this.handleAnnexes.bind(this);
        this.validatePages = this.validatePages.bind(this);
        url = window["cfgApiBaseUrl"] + "/api/downloadFile/" + `${encodeURI(this.props.match.params.name)}.pdf`;
    }

    async componentDidMount() {
        document.body.style = 'background: none;';
        document.body.style = 'background-image: ../images/pattern_news.png;';
        await fetch(window["cfgApiBaseUrl"] + `/api/getPages/${encodeURI(this.props.match.params.name)}.pdf`, {
            method: 'POST'
        }).then(
            response => {
                return response.json();
            }
        ).then(
            pdfDocument => {
                if (pdfDocument.status) {
                    throw new Error("Ocurrió algún error.");
                } else {
                    if (pdfDocument.coverPage === 0) {
                        this.setState({
                            noCoverPage: true
                        });
                    } else {
                        this.setState({
                            coverPage: pdfDocument.coverPage
                        });
                    }

                    if (pdfDocument.generalIndexStartPage === 0) {
                        this.setState({
                            noGeneralIndex: true
                        });
                    } else {
                        this.setState({
                            generalIndexStartPage: pdfDocument.generalIndexStartPage,
                            generalIndexEndPage: pdfDocument.generalIndexEndPage
                        });
                    }

                    if (pdfDocument.figureIndexStartPage === 0) {
                        this.setState({
                            noFigureIndex: true
                        });
                    } else {
                        this.setState({
                            figureIndexStartPage: pdfDocument.figureIndexStartPage,
                            figureIndexEndPage: pdfDocument.figureIndexEndPage
                        });
                    }
                    if (pdfDocument.tableIndexStartPage === 0) {
                        this.setState({
                            noTableIndex: true
                        });
                    } else {
                        this.setState({
                            tableIndexStartPage: pdfDocument.tableIndexStartPage,
                            tableIndexEndPage: pdfDocument.tableIndexEndPage
                        });
                    }
                    if (pdfDocument.bibliographyStartPage === 0) {
                        this.setState({
                            noBibliography: true
                        });
                    } else {
                        this.setState({
                            bibliographyStartPage: pdfDocument.bibliographyStartPage,
                            bibliographyEndPage: pdfDocument.bibliographyEndPage
                        });
                    }
                    if (pdfDocument.annexesStartPage === 0) {
                        this.setState({
                            noAnnexes: true
                        });
                    } else {
                        this.setState({
                            annexesStartPage: pdfDocument.annexesStartPage,
                            annexesEndPage: pdfDocument.annexesEndPage
                        });
                    }
                    this.setState({
                        totalPages: pdfDocument.totalPages
                    });
                }


            }
        ).catch(
            error => {
                this.setState({ error: true });
            }
        );
        this.setState({ isLoading: false });
    }

    handleCoverPage() {
        this.setState({
            noCoverPage: !this.state.noCoverPage
        });
        if (this.state.noCoverPage) {
            this.setState({
                coverPage: 1
            });
        } else {
            this.setState({
                coverPage: 0
            });
        }
    }

    handleGeneralIndex() {
        this.setState({
            noGeneralIndex: !this.state.noGeneralIndex
        });
        if (this.state.noGeneralIndex) {
            this.setState({
                generalIndexStartPage: 1,
                generalIndexEndPage: 1
            });
        } else {
            this.setState({
                generalIndexStartPage: 0,
                generalIndexEndPage: 0
            });
        }
    }

    handleFigureIndex() {
        this.setState({
            noFigureIndex: !this.state.noFigureIndex
        });
        if (this.state.noFigureIndex) {
            this.setState({
                figureIndexStartPage: 1,
                figureIndexEndPage: 1,
            });
        } else {
            this.setState({
                figureIndexStartPage: 0,
                figureIndexEndPage: 0
            });
        }
    }

    handleTableIndex() {
        this.setState({
            noTableIndex: !this.state.noTableIndex
        });
        if (this.state.noTableIndex) {
            this.setState({
                tableIndexStartPage: 1,
                tableIndexEndPage: 1
            });
        } else {
            this.setState({
                tableIndexStartPage: 0,
                tableIndexEndPage: 0
            });
        }
    }

    handleBibliography() {
        this.setState({
            noBibliography: !this.state.noBibliography
        });
        if (this.state.noBibliography) {
            this.setState({
                bibliographyStartPage: 1,
                bibliographyEndPage: 1,
                bibliograhyType: "UCB"
            });
        } else {
            this.setState({
                bibliographyStartPage: 0,
                bibliographyEndPage: 0,
                bibliograhyType: "-"
            });
        }
    }

    handleAnnexes() {
        this.setState({
            noAnnexes: !this.state.noAnnexes
        });
        if (this.state.noAnnexes) {
            this.setState({
                annexesStartPage: 1,
                annexesEndPage: 1
            });
        } else {
            this.setState({
                annexesStartPage: 0,
                annexesEndPage: 0
            });
        }
    }

    handleChange(event) {
        this.setState({ [event.target.name]: event.target.type === 'number' ? parseInt(event.target.value) : event.target.value });
    }

    handleSubmit(event) {
        event.preventDefault();
        if (this.validatePages()) {
            const searchParams = new URLSearchParams();
            searchParams.set("coverPage", this.state.coverPage);
            searchParams.set("generalIndexStartPage", this.state.generalIndexStartPage);
            searchParams.set("generalIndexEndPage", this.state.generalIndexEndPage);
            searchParams.set("figureIndexStartPage", this.state.figureIndexStartPage);
            searchParams.set("figureIndexEndPage", this.state.figureIndexEndPage);
            searchParams.set("tableIndexStartPage", this.state.tableIndexStartPage);
            searchParams.set("tableIndexEndPage", this.state.tableIndexEndPage);
            searchParams.set("bibliographyStartPage", this.state.bibliographyStartPage);
            searchParams.set("bibliographyEndPage", this.state.bibliographyEndPage);
            searchParams.set("bibliograhyType", this.state.bibliograhyType);
            searchParams.set("annexesStartPage", this.state.annexesStartPage);
            searchParams.set("annexesEndPage", this.state.annexesEndPage);
            const parameters = searchParams.toString();
            this.props.history.push(`/verResultados/${encodeURI(this.props.match.params.name)}` + `?${parameters}`);
        } else {
            this.setState({
                validInputs: false
            });
        }
    }

    validatePages() {
        const { coverPage, generalIndexStartPage, generalIndexEndPage, figureIndexStartPage, figureIndexEndPage,
            tableIndexStartPage, tableIndexEndPage, bibliographyStartPage,
            bibliographyEndPage, annexesStartPage, annexesEndPage, totalPages } = this.state;
        if (!this.state.noCoverPage && coverPage < 1 || coverPage > totalPages) {
            return false;
        }
        if (!this.state.noGeneralIndex && generalIndexStartPage < 1 || generalIndexStartPage > totalPages) {
            return false;
        }
        if (!this.state.noGeneralIndex && generalIndexEndPage < 1 || generalIndexEndPage > totalPages) {
            return false;
        }
        if (!this.state.noFigureIndex && figureIndexStartPage < 1 || figureIndexStartPage > totalPages) {
            return false;
        }
        if (!this.state.noFigureIndex && figureIndexEndPage < 1 || figureIndexEndPage > totalPages) {
            return false;
        }
        if (!this.state.noTableIndex && tableIndexStartPage < 1 || tableIndexStartPage > totalPages) {
            return false;
        }
        if (!this.state.noTableIndex && tableIndexEndPage < 1 || tableIndexEndPage > totalPages) {
            return false;
        }
        if (!this.state.noBibliography && bibliographyStartPage < 1 || bibliographyStartPage > totalPages) {
            return false;
        }
        if (!this.state.noAnnexes && annexesStartPage < 1 || annexesStartPage > totalPages) {
            return false;
        }
        if (!this.state.noAnnexes && annexesEndPage < 1 || annexesEndPage > totalPages) {
            return false;
        }
        if (generalIndexStartPage > generalIndexEndPage || figureIndexStartPage > figureIndexEndPage || tableIndexStartPage > tableIndexEndPage ||
            bibliographyStartPage > bibliographyEndPage || annexesStartPage > annexesEndPage) {
            return false;
        }
        return true;
    }

    nextStep(event) {
        event.preventDefault();
        if (this.validatePages()) {
            let currentStep = this.state.currentStep
            currentStep = currentStep >= 5 ? 6 : currentStep + 1
            this.setState({
                validInputs: true,
                currentStep: currentStep
            });
        } else {
            this.setState({
                validInputs: false
            });
        }
    }

    previousStep(event) {
        event.preventDefault();
        if (this.validatePages()) {
            let currentStep = this.state.currentStep
            currentStep = currentStep <= 1 ? 1 : currentStep - 1
            this.setState({
                validInputs: true,
                currentStep: currentStep
            });
        } else {
            this.setState({
                validInputs: false
            });
        }
    }

    getNumberbuttons() {
        return (
            <div className="row">
                <div className="col-lg-2">
                    <center>
                        {this.state.currentStep >= 1 ? (
                            <button type="button" className="btn-circle-blue" disabled> 1 </button>
                        ) : (<button type="button" className="btn-circle-white" disabled> 1 </button>)}
                        <p >Carátula </p>
                    </center>
                </div>

                <div className="col-lg-2">
                    <center>
                        {this.state.currentStep >= 2 ? (
                            <button type="button" className="btn-circle-blue" disabled> 2 </button>
                        ) : (<button type="button" className="btn-circle-white" disabled> 2 </button>)}
                        <p>Índice General</p>
                    </center>
                </div>

                <div className="col-lg-2">
                    <center>
                        {this.state.currentStep >= 3 ? (
                            <button type="button" className="btn-circle-blue" disabled> 3 </button>
                        ) : (<button type="button" className="btn-circle-white" disabled> 3 </button>)}
                        <p>Índice de Figuras</p>
                    </center>
                </div>

                <div className="col-lg-2">
                    <center>
                        {this.state.currentStep >= 4 ? (
                            <button type="button" className="btn-circle-blue" disabled> 4 </button>
                        ) : (<button type="button" className="btn-circle-white" disabled> 4 </button>)}
                        <p>Índice de Tablas</p>
                    </center>
                </div>
                <div className="col-lg-2">
                    <center>
                        {this.state.currentStep >= 5 ? (
                            <button type="button" className="btn-circle-blue" disabled> 5 </button>
                        ) : (<button type="button" className="btn-circle-white" disabled> 5 </button>)}
                        <p>Bibliografía</p>
                    </center>
                </div>
                <div className="col-lg-2">
                    <center>
                        {this.state.currentStep >= 6 ? (
                            <button type="button" className="btn-circle-blue" disabled> 6 </button>
                        ) : (<button type="button" className="btn-circle-white" disabled> 6 </button>)}
                        <p>Anexos</p>
                    </center>
                </div>
            </div>
        );
    }


    render() {
        if (this.state.isLoading) {
            return (
                <div>
                    <div className="mynavbar">
                        <center>
                            <Link to="/">
                                <img src={ucbImage} />
                            </Link>
                        </center>
                    </div>
                    <div className="center-loader">
                        <center>
                            <div className="lds-spinner"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div>
                            <h5> Calibrando páginas...</h5>
                        </center>
                    </div>
                </div>);
        }
        if (this.state.error) {
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
                                    <h5 className="alert alert-danger" role="alert"> Error al cargar el archivo PDF</h5>
                                    <Link to="/">
                                        <button type="button" className="btn btn-outline-primary btn-lg"> Intenta de nuevo <FontAwesomeIcon icon={faRedo} /></button>
                                    </Link>
                                </center>
                            </div>
                        </div>
                    </div>
                </div>);
        }
        var numberButtons = this.getNumberbuttons();
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
                                <h3 className="text-guide-1"> Calibración de páginas</h3>
                                <p className="text-guide-3"> Por favor verifique el rango de páginas de cada sección y modifique el rango de páginas de ser necesario </p>
                            </center>
                        </div>
                    </div>
                    {numberButtons}
                    <StepCover
                        currentStep={this.state.currentStep}
                        handleChange={this.handleChange}
                        coverPage={this.state.coverPage}
                        url={url}
                        handleCoverPage={this.handleCoverPage}
                        noCoverPage={this.state.noCoverPage}
                        nextStep={this.nextStep}
                        validInputs={this.state.validInputs}
                    />

                    <StepGeneralIndex
                        currentStep={this.state.currentStep}
                        handleChange={this.handleChange}
                        generalIndexStartPage={this.state.generalIndexStartPage}
                        generalIndexEndPage={this.state.generalIndexEndPage}
                        url={url}
                        handleGeneralIndex={this.handleGeneralIndex}
                        noGeneralIndex={this.state.noGeneralIndex}
                        nextStep={this.nextStep}
                        previousStep={this.previousStep}
                        validInputs={this.state.validInputs}
                    />

                    <StepFigureIndex
                        currentStep={this.state.currentStep}
                        handleChange={this.handleChange}
                        figureIndexStartPage={this.state.figureIndexStartPage}
                        figureIndexEndPage={this.state.figureIndexEndPage}
                        url={url}
                        handleFigureIndex={this.handleFigureIndex}
                        noFigureIndex={this.state.noFigureIndex}
                        nextStep={this.nextStep}
                        previousStep={this.previousStep}
                        validInputs={this.state.validInputs}
                    />

                    <StepTableIndex
                        currentStep={this.state.currentStep}
                        handleChange={this.handleChange}
                        tableIndexStartPage={this.state.tableIndexStartPage}
                        tableIndexEndPage={this.state.tableIndexEndPage}
                        url={url}
                        handleTableIndex={this.handleTableIndex}
                        noTableIndex={this.state.noTableIndex}
                        nextStep={this.nextStep}
                        previousStep={this.previousStep}
                        validInputs={this.state.validInputs}
                    />

                    <StepBiography
                        currentStep={this.state.currentStep}
                        handleChange={this.handleChange}
                        bibliographyStartPage={this.state.bibliographyStartPage}
                        bibliographyEndPage={this.state.bibliographyEndPage}
                        bibliograhyType={this.state.bibliograhyType}
                        url={url}
                        handleBibliography={this.handleBibliography}
                        noBibliography={this.state.noBibliography}
                        nextStep={this.nextStep}
                        previousStep={this.previousStep}
                        validInputs={this.state.validInputs}
                    />

                    <StepAnnexed
                        currentStep={this.state.currentStep}
                        handleChange={this.handleChange}
                        annexesStartPage={this.state.annexesStartPage}
                        annexesEndPage={this.state.annexesEndPage}
                        url={url}
                        handleAnnexes={this.handleAnnexes}
                        noAnnexes={this.state.noAnnexes}
                        previousStep={this.previousStep}
                        handleSubmit={this.handleSubmit}
                        validInputs={this.state.validInputs}
                    />

                </div>
            </div>
        );
    }
}

export default CalibratePages;
