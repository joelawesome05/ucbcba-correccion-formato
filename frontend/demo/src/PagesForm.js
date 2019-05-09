import React, { Component } from 'react';
import PDF from 'react-pdf-js';

import StepCover from "./wizards/StepCover"
import StepGeneralIndex from "./wizards/StepGeneralIndex"
import StepFigureIndex from "./wizards/StepFigureIndex"
import StepTableIndex from "./wizards/StepTableIndex"
import StepBiography from "./wizards/StepBiography"
import StepAnnexed from "./wizards/StepAnnexed"
import { Link } from 'react-router-dom';

import "./style/PagesForm.css";

var url = " ";
class PagesForm extends Component {

    constructor(props) {
        super(props);
        this.state = {
            currentStep: 1,
            coverPage: '',
            generalIndexPageStart: '',
            generalIndexPageEnd: '',
            figureIndexPageStart: '',
            figureIndexPageEnd: '',
            tableIndexPageStart: '',
            tableIndexPageEnd: '',
            biographyPageStart: '',
            biographyPageEnd: '',
            annexedPageStart: '',
            annexedPageEnd: '',
            noCoverPage: false,
            noGeneralIndex: false,
            noFigureIndex: false,
            noTableIndex: false,
            noBiography: false,
            noAnnexes: false,
            isLoading: true,
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.nextStep = this.nextStep.bind(this)
        this.previousStep = this.previousStep.bind(this);
        this.getNumbebuttons = this.getNumbebuttons.bind(this);
        this.goStep1 = this.goStep1.bind(this);
        this.goStep2 = this.goStep2.bind(this);
        this.goStep3 = this.goStep3.bind(this);
        this.goStep4 = this.goStep4.bind(this);
        this.goStep5 = this.goStep5.bind(this);
        this.goStep6 = this.goStep6.bind(this);
        this.handleCoverPage = this.handleCoverPage.bind(this);
        this.handleGeneralIndex = this.handleGeneralIndex.bind(this);
        this.handleFigureIndex = this.handleFigureIndex.bind(this);
        this.handleTableIndex = this.handleTableIndex.bind(this);
        this.handleBibliography = this.handleBibliography.bind(this);
        this.handleAnnexes = this.handleAnnexes.bind(this);
        url = "/api/downloadFile/" + `${encodeURI(this.props.match.params.name)}.pdf`;
    }

    async componentDidMount() {
        document.body.style = 'background: none;';
        document.body.style = 'background-image: ./images/pattern_news.png;';
        var pdfDocument = await (await fetch(`/api/getPages/${encodeURI(this.props.match.params.name)}.pdf`)).json();
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
                generalIndexPageStart: pdfDocument.generalIndexStartPage,
                generalIndexPageEnd: pdfDocument.generalIndexEndPage
            });
        }

        if (pdfDocument.figureIndexStartPage === 0) {
            this.setState({
                noFigureIndex: true
            });
        } else {
            this.setState({
                figureIndexPageStart: pdfDocument.figureIndexStartPage,
                figureIndexPageEnd: pdfDocument.figureIndexEndPage
            });
        }
        if (pdfDocument.tableIndexStartPage === 0) {
            this.setState({
                noTableIndex: true
            });
        } else {
            this.setState({
                tableIndexPageStart: pdfDocument.tableIndexStartPage,
                tableIndexPageEnd: pdfDocument.tableIndexEndPage
            });
        }
        if (pdfDocument.bibliographyStartPage === 0) {
            this.setState({
                noBiography: true
            });
        } else {
            this.setState({
                biographyPageStart: pdfDocument.bibliographyStartPage,
                biographyPageEnd: pdfDocument.bibliographyEndPage
            });
        }
        if (pdfDocument.annexesStartPage === 0) {
            this.setState({
                noAnnexes: true
            });
        } else {
            this.setState({
                annexedPageStart: pdfDocument.annexesStartPage,
                annexedPageEnd: pdfDocument.annexesEndPage
            });
        }

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
                coverPage: ''
            });
        }
    }

    handleGeneralIndex() {
        this.setState({
            noGeneralIndex: !this.state.noGeneralIndex
        });
        if (this.state.noGeneralIndex) {
            this.setState({
                generalIndexPageStart: 1,
                generalIndexPageEnd: 1
            });
        } else {
            this.setState({
                generalIndexPageStart: '',
                generalIndexPageEnd: ''
            });
        }
    }

    handleFigureIndex() {
        this.setState({
            noFigureIndex: !this.state.noFigureIndex
        });
        if (this.state.noFigureIndex) {
            this.setState({
                figureIndexPageStart: 1,
                figureIndexPageEnd: 1,
            });
        } else {
            this.setState({
                figureIndexPageStart: '',
                figureIndexPageEnd: ''
            });
        }
    }

    handleTableIndex() {
        this.setState({
            noTableIndex: !this.state.noTableIndex
        });
        if (this.state.noTableIndex) {
            this.setState({
                tableIndexPageStart: 1,
                tableIndexPageEnd: 1
            });
        } else {
            this.setState({
                tableIndexPageStart: '',
                tableIndexPageEnd: ''
            });
        }
    }

    handleBibliography() {
        this.setState({
            noBiography: !this.state.noBiography
        });
        if (this.state.noBiography) {
            this.setState({
                biographyPageStart: 1,
                biographyPageEnd: 1
            });
        } else {
            this.setState({
                biographyPageStart: '',
                biographyPageEnd: ''
            });
        }
    }

    handleAnnexes() {
        this.setState({
            noAnnexes: !this.state.noAnnexes
        });
        if (this.state.noAnnexes) {
            this.setState({
                annexedPageStart: 1,
                annexedPageEnd: 1
            });
        } else {
            this.setState({
                annexedPageStart: '',
                annexedPageEnd: ''
            });
        }
    }

    handleChange(event) {
        this.setState({ [event.target.name]: event.target.type === 'number' ? parseInt(event.target.value) : event.target.value });
    }

    handleSubmit(event) {
        event.preventDefault();
        const searchParams = new URLSearchParams();
        searchParams.set("coverPage", this.state.coverPage);
        searchParams.set("generalIndexStartPage", this.state.generalIndexPageStart);
        searchParams.set("generalIndexEndPage", this.state.generalIndexPageEnd);
        searchParams.set("figureIndexStartPage", this.state.figureIndexPageStart);
        searchParams.set("figureIndexEndPage", this.state.figureIndexPageEnd);
        searchParams.set("tableIndexStartPage", this.state.tableIndexPageStart);
        searchParams.set("tableIndexEndPage", this.state.tableIndexPageEnd);
        searchParams.set("bibliographyStartPage", this.state.biographyPageStart);
        searchParams.set("bibliographyEndPage", this.state.biographyPageEnd);
        searchParams.set("annexesStartPage", this.state.annexedPageStart);
        searchParams.set("annexesEndPage", this.state.annexedPageEnd);
        const parameters = searchParams.toString();
        this.props.history.push(`/verResultados/${encodeURI(this.props.match.params.name)}` + `?${parameters}`);
    }

    goStep1() {
        this.setState({
            currentStep: 1
        });
    }

    goStep2() {
        this.setState({
            currentStep: 2
        });
    }

    goStep3() {
        this.setState({
            currentStep: 3
        });
    }

    goStep4() {
        this.setState({
            currentStep: 4
        });
    }

    goStep5() {
        this.setState({
            currentStep: 5
        });
    }

    goStep6() {
        this.setState({
            currentStep: 6
        });
    }
    nextStep() {
        let currentStep = this.state.currentStep
        currentStep = currentStep >= 5 ? 6 : currentStep + 1
        this.setState({
            currentStep: currentStep
        })
    }

    previousStep() {
        let currentStep = this.state.currentStep
        currentStep = currentStep <= 1 ? 1 : currentStep - 1
        this.setState({
            currentStep: currentStep
        })
    }

    getNumbebuttons() {
        if (this.state.currentStep == 1) {
            return (
                <div className="row">
                    <div className="col-lg-2">
                        <center>
                            <button type="button" className="btn-circle-blue" disabled> 1 </button>
                            <p >Carátula </p>
                        </center>
                    </div>

                    <div className="col-lg-2">
                        <center>
                            <button type="button" className="btn-circle-white" onClick={this.goStep2}> 2 </button>
                            <p>Índice General</p>
                        </center>
                    </div>

                    <div className="col-lg-2">
                        <center>
                            <button type="button" className="btn-circle-white" onClick={this.goStep3}> 3 </button>
                            <p>Índice de Figuras</p>
                        </center>
                    </div>

                    <div className="col-lg-2">
                        <center>
                            <button type="button" className="btn-circle-white" onClick={this.goStep4}> 4 </button>
                            <p>Índice de Tablas</p>
                        </center>
                    </div>
                    <div className="col-lg-2">
                        <center>
                            <button type="button" className="btn-circle-white" onClick={this.goStep5}> 5 </button>
                            <p>Bibliografía</p>
                        </center>
                    </div>
                    <div className="col-lg-2">
                        <center>
                            <button type="button" className="btn-circle-white" onClick={this.goStep6}> 6 </button>
                            <p>Anexos</p>
                        </center>
                    </div>
                </div>
            );
        }
        if (this.state.currentStep == 2) {
            return (
                <div className="row">
                    <div className="col-lg-2">
                        <center>
                            <button type="button" className="btn-circle-white" onClick={this.goStep1}> 1 </button>
                            <p >Carátula </p>
                        </center>
                    </div>

                    <div className="col-lg-2">
                        <center>
                            <button type="button" className="btn-circle-blue" disabled> 2 </button>
                            <p>Índice General</p>
                        </center>
                    </div>

                    <div className="col-lg-2">
                        <center>
                            <button type="button" className="btn-circle-white" onClick={this.goStep3}> 3 </button>
                            <p>Índice de Figuras</p>
                        </center>
                    </div>

                    <div className="col-lg-2">
                        <center>
                            <button type="button" className="btn-circle-white" onClick={this.goStep4}> 4 </button>
                            <p>Índice de Tablas</p>
                        </center>
                    </div>
                    <div className="col-lg-2">
                        <center>
                            <button type="button" className="btn-circle-white" onClick={this.goStep5}> 5 </button>
                            <p>Bibliografía</p>
                        </center>
                    </div>
                    <div className="col-lg-2">
                        <center>
                            <button type="button" className="btn-circle-white" onClick={this.goStep6}> 6 </button>
                            <p>Anexos</p>
                        </center>
                    </div>
                </div>
            );
        }
        if (this.state.currentStep == 3) {
            return (
                <div className="row">
                    <div className="col-lg-2">
                        <center>
                            <button type="button" className="btn-circle-white" onClick={this.goStep1}> 1 </button>
                            <p >Carátula </p>
                        </center>
                    </div>

                    <div className="col-lg-2">
                        <center>
                            <button type="button" className="btn-circle-white" onClick={this.goStep2}> 2 </button>
                            <p>Índice General</p>
                        </center>
                    </div>

                    <div className="col-lg-2">
                        <center>
                            <button type="button" className="btn-circle-blue" disabled> 3 </button>
                            <p>Índice de Figuras</p>
                        </center>
                    </div>

                    <div className="col-lg-2">
                        <center>
                            <button type="button" className="btn-circle-white" onClick={this.goStep4}> 4 </button>
                            <p>Índice de Tablas</p>
                        </center>
                    </div>
                    <div className="col-lg-2">
                        <center>
                            <button type="button" class="btn-circle-white" onClick={this.goStep5}> 5 </button>
                            <p>Bibliografía</p>
                        </center>
                    </div>
                    <div className="col-lg-2">
                        <center>
                            <button type="button" class="btn-circle-white" onClick={this.goStep6}> 6 </button>
                            <p>Anexos</p>
                        </center>
                    </div>
                </div>
            );
        }
        if (this.state.currentStep == 4) {
            return (
                <div className="row">
                    <div className="col-lg-2">
                        <center>
                            <button type="button" class="btn-circle-white" onClick={this.goStep1}> 1 </button>
                            <p >Carátula </p>
                        </center>
                    </div>

                    <div className="col-lg-2">
                        <center>
                            <button type="button" class="btn-circle-white" onClick={this.goStep2}> 2 </button>
                            <p>Índice General</p>
                        </center>
                    </div>

                    <div className="col-lg-2">
                        <center>
                            <button type="button" class="btn-circle-white" onClick={this.goStep3}> 3 </button>
                            <p>Índice de Figuras</p>
                        </center>
                    </div>

                    <div className="col-lg-2">
                        <center>
                            <button type="button" class="btn-circle-blue" disabled> 4 </button>
                            <p>Índice de Tablas</p>
                        </center>
                    </div>
                    <div className="col-lg-2">
                        <center>
                            <button type="button" class="btn-circle-white" onClick={this.goStep5}> 5 </button>
                            <p>Bibliografía</p>
                        </center>
                    </div>
                    <div className="col-lg-2">
                        <center>
                            <button type="button" class="btn-circle-white" onClick={this.goStep6}> 6 </button>
                            <p>Anexos</p>
                        </center>
                    </div>
                </div>
            );
        }
        if (this.state.currentStep == 5) {
            return (
                <div className="row">
                    <div className="col-lg-2">
                        <center>
                            <button type="button" class="btn-circle-white" onClick={this.goStep1}> 1 </button>
                            <p >Carátula </p>
                        </center>
                    </div>

                    <div className="col-lg-2">
                        <center>
                            <button type="button" class="btn-circle-white" onClick={this.goStep2}> 2 </button>
                            <p>Índice General</p>
                        </center>
                    </div>

                    <div className="col-lg-2">
                        <center>
                            <button type="button" class="btn-circle-white" onClick={this.goStep3}> 3 </button>
                            <p>Índice de Figuras</p>
                        </center>
                    </div>

                    <div className="col-lg-2">
                        <center>
                            <button type="button" class="btn-circle-white" onClick={this.goStep4}> 4 </button>
                            <p>Índice de Tablas</p>
                        </center>
                    </div>
                    <div className="col-lg-2">
                        <center>
                            <button type="button" class="btn-circle-blue" disabled> 5 </button>
                            <p>Bibliografía</p>
                        </center>
                    </div>
                    <div className="col-lg-2">
                        <center>
                            <button type="button" class="btn-circle-white" onClick={this.goStep6}> 6 </button>
                            <p>Anexos</p>
                        </center>
                    </div>
                </div>
            );
        }
        if (this.state.currentStep == 6) {
            return (
                <div className="row">
                    <div className="col-lg-2">
                        <center>
                            <button type="button" class="btn-circle-white" onClick={this.goStep1}> 1 </button>
                            <p >Carátula </p>
                        </center>
                    </div>

                    <div className="col-lg-2">
                        <center>
                            <button type="button" class="btn-circle-white" onClick={this.goStep2}> 2 </button>
                            <p>Índice General</p>
                        </center>
                    </div>

                    <div className="col-lg-2">
                        <center>
                            <button type="button" class="btn-circle-white" onClick={this.goStep3} > 3 </button>
                            <p>Índice de Figuras</p>
                        </center>
                    </div>

                    <div className="col-lg-2">
                        <center>
                            <button type="button" class="btn-circle-white" onClick={this.goStep4}> 4 </button>
                            <p>Índice de Tablas</p>
                        </center>
                    </div>
                    <div className="col-lg-2">
                        <center>
                            <button type="button" class="btn-circle-white" onClick={this.goStep5}> 5 </button>
                            <p>Bibliografía</p>
                        </center>
                    </div>
                    <div className="col-lg-2">
                        <center>
                            <button type="button" class="btn-circle-blue" disabled> 6 </button>
                            <p>Anexos</p>
                        </center>
                    </div>
                </div>
            );
        }
        return null;
    }

    render() {
        if (this.state.isLoading) {
            return (
                <div>
                    <div className="mynavbar">
                        <center>
                            <Link to="/">
                                <img src={require('./images/ucbcba.png')} />
                            </Link>
                        </center>
                    </div>
                    <div className="center-loader">
                        <center>
                            <div class="lds-spinner"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div>
                            <h5> Calibrando páginas...</h5>
                        </center>
                    </div>
                </div>);
        }
        var numberButtons = this.getNumbebuttons();
        return (
            <div>
                <div className="mynavbar">
                    <center>
                        <Link to="/">
                            <img src={require('./images/ucbcba.png')} />
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
                    />

                    <StepGeneralIndex
                        currentStep={this.state.currentStep}
                        handleChange={this.handleChange}
                        generalIndexPageStart={this.state.generalIndexPageStart}
                        generalIndexPageEnd={this.state.generalIndexPageEnd}
                        url={url}
                        handleGeneralIndex={this.handleGeneralIndex}
                        noGeneralIndex={this.state.noGeneralIndex}
                        nextStep={this.nextStep}
                        previousStep={this.previousStep}
                    />

                    <StepFigureIndex
                        currentStep={this.state.currentStep}
                        handleChange={this.handleChange}
                        figureIndexPageStart={this.state.figureIndexPageStart}
                        figureIndexPageEnd={this.state.figureIndexPageEnd}
                        url={url}
                        handleFigureIndex={this.handleFigureIndex}
                        noFigureIndex={this.state.noFigureIndex}
                        nextStep={this.nextStep}
                        previousStep={this.previousStep}
                    />

                    <StepTableIndex
                        currentStep={this.state.currentStep}
                        handleChange={this.handleChange}
                        tableIndexPageStart={this.state.tableIndexPageStart}
                        tableIndexPageEnd={this.state.tableIndexPageEnd}
                        url={url}
                        handleTableIndex={this.handleTableIndex}
                        noTableIndex={this.state.noTableIndex}
                        nextStep={this.nextStep}
                        previousStep={this.previousStep}
                    />

                    <StepBiography
                        currentStep={this.state.currentStep}
                        handleChange={this.handleChange}
                        biographyPageStart={this.state.biographyPageStart}
                        biographyPageEnd={this.state.biographyPageEnd}
                        url={url}
                        handleBibliography={this.handleBibliography}
                        noBiography={this.state.noBiography}
                        nextStep={this.nextStep}
                        previousStep={this.previousStep}
                    />

                    <StepAnnexed
                        currentStep={this.state.currentStep}
                        handleChange={this.handleChange}
                        annexedPageStart={this.state.annexedPageStart}
                        annexedPageEnd={this.state.annexedPageEnd}
                        url={url}
                        handleAnnexes={this.handleAnnexes}
                        noAnnexes={this.state.noAnnexes}
                        previousStep={this.previousStep}
                        handleSubmit={this.handleSubmit}
                    />

                </div>
            </div>
        );
    }
}

export default PagesForm;
