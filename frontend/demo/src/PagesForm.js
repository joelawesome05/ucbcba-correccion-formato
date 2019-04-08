import React, { Component } from 'react';
import PDF from 'react-pdf-js';
import PdfPreview from "./PdfPreview";

var url = " ";
class PagesForm extends Component {

    constructor(props) {
        super(props);
        this.state = {
            coverPage: 0,
            generalIndexPageStart: 0,
            generalIndexPageEnd: 0,
            figureTableIndexPageStart: 0,
            figureTableIndexPageEnd: 0,
            biographyPageStart: 0,
            biographyPageEnd: 0,
            annexedPageStart: 0,
            annexedPageEnd: 0,
            isLoading: true,
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        url = "/api/downloadFile/" + `${encodeURI(this.props.match.params.name)}`;
    }

    async componentDidMount() {
        var pages = await (await fetch(`/api/getPages/${encodeURI(this.props.match.params.name)}`)).json();
        this.setState({
            coverPage: pages[0],
            generalIndexPageStart: pages[1],
            generalIndexPageEnd: pages[2],
            figureTableIndexPageStart: pages[3],
            figureTableIndexPageEnd: pages[4],
            biographyPageStart: pages[5],
            biographyPageEnd: pages[6],
            annexedPageStart: pages[7],
            annexedPageEnd: pages[8]
        });
        this.setState({ isLoading: false });
    }

    handleChange(event) {
        this.setState({ [event.target.name]: event.target.type === 'number' ? parseInt(event.target.value) : event.target.value });
    }


    handleSubmit(event) {
        event.preventDefault();
        const searchParams = new URLSearchParams();
        searchParams.set("coverPage", this.state.coverPage);
        searchParams.set("generalIndexPageStart", this.state.generalIndexPageStart);
        searchParams.set("generalIndexPageEnd", this.state.generalIndexPageEnd);
        searchParams.set("figureTableIndexPageEnd", this.state.figureTableIndexPageEnd);
        searchParams.set("biographyPageStart", this.state.biographyPageStart);
        searchParams.set("annexedPageStart", this.state.annexedPageStart);
        const parameters = searchParams.toString();
        setTimeout(function () { this.props.history.push(`/verResultados/${encodeURI(this.props.match.params.name)}` + `?${parameters}`) }.bind(this), 2000);

    }

    render() {
        if (this.state.isLoading) {
            return <p style={{ color: "black" }}>Cargando...</p>;
        }
        return (
            <div style={{ color: "black" }}>
                <center>
                    <h3> Sistema de ayuda para la detección y corrección de errores de formato en trabajos académicos</h3>
                </center>
                <form onSubmit={this.handleSubmit} id="pagesForm" name="pagesForm">
                    <div className="row">
                        <div className="col-sm-4">
                            <label>
                                Página de la carátula:
                            </label>
                            <input type="number" name="coverPage" value={this.state.coverPage} onChange={this.handleChange} />
                        </div>
                        <PdfPreview
                            url={url}
                            pageStart={this.state.coverPage}
                            pageEnd={this.state.coverPage}
                            section="Carátula"
                        />
                    </div>
                    <br></br>
                    <div className="row">
                        <div className="col-sm-4">
                            <label>
                                Página inicio del índice general:
                            </label>
                            <input type="number" name="generalIndexPageStart" value={this.state.generalIndexPageStart} onChange={this.handleChange} />
                            <br></br>
                            <label>
                                Página fin del índice general:
                            </label>
                            <input type="number" name="generalIndexPageEnd" value={this.state.generalIndexPageEnd} onChange={this.handleChange} />
                        </div>
                        <PdfPreview
                            url={url}
                            pageStart={this.state.generalIndexPageStart}
                            pageEnd={this.state.generalIndexPageEnd}
                            section="Índice General"
                        />
                    </div>
                    <br></br>
                    <div className="row">
                        <div className="col-sm-4">
                            <label>
                                Página inicio del índice de tablas y figuras:
                             </label>
                            <input type="number" name="figureTableIndexPageStart" value={this.state.figureTableIndexPageStart} onChange={this.handleChange} />
                            <br></br>

                            <label>
                                Página fin del índice de tablas y figuras:
                            </label>
                            <input type="number" name="figureTableIndexPageEnd" value={this.state.figureTableIndexPageEnd} onChange={this.handleChange} />
                        </div>
                        <PdfPreview
                            url={url}
                            pageStart={this.state.figureTableIndexPageStart}
                            pageEnd={this.state.figureTableIndexPageEnd}
                            section="Índice de tablas y figuras"
                        />
                    </div>
                    <br></br>
                    <div className="row">
                        <div className="col-sm-4">
                            <label>
                                Página inicio de la bibliografía:
                            </label>
                            <input type="number" name="biographyPageStart" value={this.state.biographyPageStart} onChange={this.handleChange} />
                            <br></br>
                            <label>
                                Página fin de la bibliografía:
                            </label>
                            <input type="number" name="biographyPageEnd" value={this.state.biographyPageEnd} onChange={this.handleChange} />
                        </div>
                        <PdfPreview
                            url={url}
                            pageStart={this.state.biographyPageStart}
                            pageEnd={this.state.biographyPageEnd}
                            section="Bibliografía"
                        />
                    </div>
                    <br></br>
                    <div className="row">
                        <div className="col-sm-4">
                            <label>
                                Página inicio de los anexos:
                            </label>
                            <input type="number" name="annexedPageStart" value={this.state.annexedPageStart} onChange={this.handleChange} />
                            <br></br>
                            <label>
                                Página fin de los anexos:
                            </label>
                            <input type="number" name="annexedPageEnd" value={this.state.annexedPageEnd} onChange={this.handleChange} />
                        </div>
                        <PdfPreview
                            url={url}
                            pageStart={this.state.annexedPageStart}
                            pageEnd={this.state.annexedPageEnd}
                            section="Anexos"
                        />
                    </div>
                    <br></br>
                    <br></br>
                    <button type="submit" className="btn btn-success btn-lg btn-block">Enviar</button>
                </form>
            </div>
        );
    }
}

export default PagesForm;
