<#include "../header.ftl">
<div class="container">
    <div class="card mb-2">
        <div class="row justify-content-center mt-2">
            <form id="client" action="/api/orders/new" method="post" autocomplete="off">
                <h3 class="text-center font-italic font-weight-bold font-family">Замовлення</h3>
                <div class="col-11 block pt-5">
                    <table class="table table-hover mb-0">
                        <tbody>
                        <tr hidden>
                            <th class="align-middle" scope="row">Ідентифікатор тварини:</th>
                            <td class="td-padding">
                                <input name="animalId" placeholder="" value="<#if id??>${id}<#else></#if>"
                                       type="text" tabindex="1" required autofocus/>
                            </td>
                        </tr>
                        <tr>
                            <th class="align-middle" scope="row">Ваше ім'я та прізвище:</th>
                            <td class="td-padding">
                                <input name="userName" placeholder="" value=""
                                       type="text" tabindex="2" required autofocus/>
                            </td>
                        </tr>
                        <tr>
                            <th class="align-middle" scope="row">Ваше номер телефону:</th>
                            <td class="td-padding">
                                <input name="userPhoneNumber" placeholder="" value=""
                                       type="text" tabindex="3" required autofocus/>
                            </td>
                        </tr>
                        <tr>
                            <th class="align-middle" scope="row">Ваша електронна адреса:</th>
                            <td class="td-padding">
                                <input name="userEmail" placeholder="" value=""
                                       type="text" tabindex="4" required>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <fieldset class="row justify-content-center tm-10">
                        <button name="Submit" type="submit" class="col-4 btn btn-primary text-center submitBtn" data-submit="...Sending">
                            Надіслати
                        </button>
                    </fieldset>
                </div>
            </form>
        </div>
    </div>
</div>
<#include "../footer.ftl">

